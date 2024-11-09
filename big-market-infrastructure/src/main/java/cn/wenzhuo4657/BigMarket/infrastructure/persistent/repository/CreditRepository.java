package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.wenzhuo4657.BigMarket.domain.credit.model.aggregate.TradeAggregate;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.repository.ICreditRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserCreditAccountDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserCreditOrderDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserCreditAccount;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserCreditOrder;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.IRedisService;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.annotation.Signed;
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
    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();


//        实体转化为po
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        userCreditAccountReq.setTotalAmount(creditAccountEntity.getAdjustAmount());
        userCreditAccountReq.setAvailableAmount(creditAccountEntity.getAdjustAmount());

        UserCreditOrder userCreditOrderReq = new UserCreditOrder();
        userCreditOrderReq.setUserId(creditOrderEntity.getUserId());
        userCreditOrderReq.setOrderId(creditOrderEntity.getOrderId());
        userCreditOrderReq.setTradeName(creditOrderEntity.getTradeName().getName());
        userCreditOrderReq.setTradeType(creditOrderEntity.getTradeType().getCode());
        userCreditOrderReq.setTradeAmount(creditOrderEntity.getTradeAmount());
        userCreditOrderReq.setOutBusinessNo(creditOrderEntity.getOutBusinessNo());

        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());

        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try{
                    int updatedAddAmount = userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    if (0==updatedAddAmount) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    }
                    userCreditOrderDao.insert(userCreditOrderReq);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度异常，唯一索引冲突 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度失败 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                }
                return 1;
            });

        }finally {
            dbRouter.clear();;
            lock.unlock();
        }



    }
}
