package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.wenzhuo4657.BigMarket.domain.credit.model.aggregate.TradeAggregate;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.TaskEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.valobj.TradeTypeVO;
import cn.wenzhuo4657.BigMarket.domain.credit.repository.ICreditRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.event.EventPublisher;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.TaskDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserCreditAccountDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserCreditOrderDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.Task;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserCreditAccount;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserCreditOrder;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.IRedisService;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.annotation.Signed;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description:
 */

@Slf4j
@Repository
public class CreditRepository implements ICreditRepository {

    @Resource
    private IRedisService redisService;
    @Resource
    private UserCreditAccountDao userCreditAccountDao;
    @Resource
    private UserCreditOrderDao userCreditOrderDao;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;
    @Resource
    private TaskDao taskDao;
    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        TaskEntity taskEntity = tradeAggregate.getTaskEntity();

//        实体转化为po
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        if (creditOrderEntity.getTradeType().getCode().equals(TradeTypeVO.FORWARD.getCode())){
            userCreditAccountReq.setTotalAmount(creditAccountEntity.getAdjustAmount());
            userCreditAccountReq.setAvailableAmount(creditAccountEntity.getAdjustAmount());
        }else if (creditOrderEntity.getTradeType().getCode().equals(TradeTypeVO.REVERSE.getCode())){
            userCreditAccountReq.setTotalAmount(creditAccountEntity.getAdjustAmount().multiply(new BigDecimal("-1")));
            userCreditAccountReq.setAvailableAmount(creditAccountEntity.getAdjustAmount().multiply(new BigDecimal("-1")));
        }else {
            throw  new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        if (creditAccountEntity.getAdjustAmount().subtract(userCreditAccountReq.getTotalAmount()).longValue()<0){
            throw new AppException(ResponseCode.CREDIT_ACCOUNT_QUOTA_ERROR.getCode(),ResponseCode.CREDIT_ACCOUNT_QUOTA_ERROR.getInfo());
        }


        UserCreditOrder userCreditOrderReq = new UserCreditOrder();
        userCreditOrderReq.setUserId(creditOrderEntity.getUserId());
        userCreditOrderReq.setOrderId(creditOrderEntity.getOrderId());
        userCreditOrderReq.setTradeName(creditOrderEntity.getTradeName().getName());
        userCreditOrderReq.setTradeType(creditOrderEntity.getTradeType().getCode());
        userCreditOrderReq.setTradeAmount(creditOrderEntity.getTradeAmount());
        userCreditOrderReq.setOutBusinessNo(creditOrderEntity.getOutBusinessNo());

        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());

        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());

        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try{
//                    1,更新账户
                    int updatedAddAmount = userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    if (0==updatedAddAmount) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    }


//                    2，写入积分订单记录
                    userCreditOrderDao.insert(userCreditOrderReq);

//                    3，写入任务
                    taskDao.insert(task);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度异常，唯一索引冲突 userId:{} out_business_no:{}", userId, creditOrderEntity.getOutBusinessNo(), e);
                    throw e;

                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度失败 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                    throw e;
                }
                return 1;
            });

        }finally {
            dbRouter.clear();;
            lock.unlock();
        }

        try {
            // 发送消息【在事务外执行，如果失败还有任务补偿】
            eventPublisher.publish(task.getTopic(), task.getMessage());
            // 更新数据库记录，task 任务表
            taskDao.updateTaskSendMessageCompleted(task);
            log.info("调整账户积分记录，发送MQ消息完成 userId: {} orderId:{} topic: {}", userId, creditOrderEntity.getOrderId(), task.getTopic());
        } catch (Exception e) {
            log.error("调整账户积分记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }
    }

    @Override
    public void updateCreditAccount(BigDecimal multiply, String userId) {
        UserCreditAccount userCreditAccountReq=new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        userCreditAccountReq.setAvailableAmount(multiply);
        userCreditAccountReq.setTotalAmount(multiply);
        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
    }

    @Override
    public CreditAccountEntity queryUserCreditAccount(String userId) {
        UserCreditAccount userCreditAccountReq=new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        try{
            dbRouter.clear();
            UserCreditAccount userCreditAccount= userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
            if (Objects.isNull(userCreditAccount)){
                userCreditAccount=UserCreditAccount.builder()
                        .userId(userId)
                        .accountStatus("open")
                        .availableAmount(new BigDecimal(0))
                        .totalAmount(new BigDecimal(0))
                        .build();
                userCreditAccountDao.insert(userCreditAccount);
            }
            return CreditAccountEntity.builder().userId(userId).adjustAmount(userCreditAccount.getAvailableAmount()).build();
        }catch (Exception e){
            log.error("查询用户积分账户出错，userId:{} e:{}",userId,e);
            return CreditAccountEntity.builder().userId(userId).adjustAmount(new BigDecimal(-1)).build();
        } finally{
            dbRouter.clear();
        }

    }
}
