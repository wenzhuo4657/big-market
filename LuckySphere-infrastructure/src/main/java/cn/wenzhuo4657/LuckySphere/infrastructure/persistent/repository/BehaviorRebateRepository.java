package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.repository;

import cn.wenzhuo4657.LuckySphere.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.TaskEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj.BehaviorTypeVO;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import cn.wenzhuo4657.LuckySphere.domain.rebate.repository.IBehaviorRebateRepository;
import cn.wenzhuo4657.LuckySphere.infrastructure.event.EventPublisher;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.DailyBehaviorRebateDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.TaskDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.UserBehaviorRebateOrderDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.UserCreditAccountDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.DailyBehaviorRebate;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.Task;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserBehaviorRebateOrder;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserCreditAccount;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.redis.RedissonService;
import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/4
 * @description: 行为返利仓储实现
 */
@Slf4j
@Repository
public class BehaviorRebateRepository implements IBehaviorRebateRepository {

    @Resource
    private DailyBehaviorRebateDao dailyBehaviorRebateDao;
    @Resource
    private UserBehaviorRebateOrderDao userBehaviorRebateOrderDao;
    @Resource
    private TaskDao taskDao;

    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private RedissonService redissonService;

    @Resource
    private UserCreditAccountDao userCreditAccountDao;

    @Override
    public boolean queryUserInfoByUserId(String userId) {
        UserCreditAccount userCreditAccountReq=new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);

        if (userCreditAccount==null){
            return  false;
        }
        return true;
    }

    @Override
    public List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO) {
        List<DailyBehaviorRebate> dailyBehaviorRebates = dailyBehaviorRebateDao.queryDailyBehaviorRebateByBehaviorType(behaviorTypeVO.getCode().toLowerCase());
        ArrayList<DailyBehaviorRebateVO> vos = new ArrayList<>(dailyBehaviorRebates.size());
        for (DailyBehaviorRebate vo:dailyBehaviorRebates){
            vos.add(DailyBehaviorRebateVO.builder()
                    .behaviorType(vo.getBehaviorType())
                    .rebateDesc(vo.getRebateDesc())
                    .rebateType(vo.getRebateType())
                    .rebateConfig(vo.getRebateConfig())
                    .build());
        }
        return vos;
    }

    @Override
    public void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates) {
        try {
            transactionTemplate.execute(status -> {
                try {
                    for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
                        BehaviorRebateOrderEntity behaviorRebateOrderEntity = behaviorRebateAggregate.getBehaviorRebateOrderEntity();
                        // 用户行为返利订单对象
                        UserBehaviorRebateOrder userBehaviorRebateOrder = new UserBehaviorRebateOrder();
                        userBehaviorRebateOrder.setUserId(behaviorRebateOrderEntity.getUserId());
                        userBehaviorRebateOrder.setOrderId(behaviorRebateOrderEntity.getOrderId());
                        userBehaviorRebateOrder.setBehaviorType(behaviorRebateOrderEntity.getBehaviorType());
                        userBehaviorRebateOrder.setRebateDesc(behaviorRebateOrderEntity.getRebateDesc());
                        userBehaviorRebateOrder.setRebateType(behaviorRebateOrderEntity.getRebateType());
                        userBehaviorRebateOrder.setRebateConfig(behaviorRebateOrderEntity.getRebateConfig());
                        userBehaviorRebateOrder.setBizId(behaviorRebateOrderEntity.getBizId());
                        userBehaviorRebateOrder.setOutBusinessNo(behaviorRebateOrderEntity.getOutBusinessNo());
                        userBehaviorRebateOrderDao.insert(userBehaviorRebateOrder);
                        // 任务对象
                        TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
                        Task task = new Task();
                        task.setUserId(taskEntity.getUserId());
                        task.setTopic(taskEntity.getTopic());
                        task.setMessageId(taskEntity.getMessageId());
                        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
                        task.setState(taskEntity.getState().getCode());
                        taskDao.insert(task);
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入返利记录，唯一索引冲突 userId: {}", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), ResponseCode.INDEX_DUP.getInfo());
                }
            });

        } finally {

        }

        for (BehaviorRebateAggregate aggregate:behaviorRebateAggregates){
            TaskEntity taskEntity = aggregate.getTaskEntity();
            Task task=new Task();
            task.setUserId(taskEntity.getUserId());
            task.setMessageId(taskEntity.getMessageId());
            try {
                eventPublisher.publish(taskEntity.getTopic(),taskEntity.getMessage());
                taskDao.updateTaskSendMessageCompleted(task);
            } catch (Exception e) {
                log.error("写入返利记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
                taskDao.updateTaskSendMessageFail(task);
            }
        }

    }

    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        UserBehaviorRebateOrder userBehaviorRebateOrderReq=new UserBehaviorRebateOrder();
        userBehaviorRebateOrderReq.setUserId(userId);
        userBehaviorRebateOrderReq.setOutBusinessNo(outBusinessNo);
        List<UserBehaviorRebateOrder> userBehaviorRebateOrders =userBehaviorRebateOrderDao.queryOrderByOutBusinessNo(userBehaviorRebateOrderReq);
        List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = new ArrayList<>(userBehaviorRebateOrders.size());
        for (UserBehaviorRebateOrder userBehaviorRebateOrder : userBehaviorRebateOrders) {
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(userBehaviorRebateOrder.getUserId())
                    .orderId(userBehaviorRebateOrder.getOrderId())
                    .behaviorType(userBehaviorRebateOrder.getBehaviorType())
                    .rebateDesc(userBehaviorRebateOrder.getRebateDesc())
                    .rebateType(userBehaviorRebateOrder.getRebateType())
                    .rebateConfig(userBehaviorRebateOrder.getRebateConfig())
                    .outBusinessNo(userBehaviorRebateOrder.getOutBusinessNo())
                    .bizId(userBehaviorRebateOrder.getBizId())
                    .build();
            behaviorRebateOrderEntities.add(behaviorRebateOrderEntity);
        }
        return behaviorRebateOrderEntities;
    }
}
