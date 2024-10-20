package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivityStateVO;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.*;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.*;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.IRedisService;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 活动仓储服务
 */
@Repository
@Slf4j
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redissonService;
    @Resource
    private RaffleActivityDao raffleActivityDao;
    @Resource
    private RaffleActivitySkuDao raffleActivitySkuDao;
    @Resource
    private RaffleActivityCountDao raffleActivityCountDao;
    @Resource
    private RaffleActivityOrderDao raffleActivityOrderDao;
    @Resource
    private RaffleActivityAccountDao raffleActivityAccountDao;

    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private IDBRouterStrategy dbRouter;


    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySku raffleActivitySku=raffleActivitySkuDao.queryBySku(sku);
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(raffleActivitySku.getStockCountSurplus())
                .build();
    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        String cacheKey=Constants.RedisKey.ACTIVITY_KEY+activityId;
        ActivityEntity entity = redissonService.getValue(cacheKey);
        if (!Objects.isNull(entity)){
            return  entity;
        }
        RaffleActivity raffleActivity =raffleActivityDao.queryByActivityId(activityId);
        entity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .strategyId(raffleActivity.getStrategyId())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        redissonService.setValue(cacheKey, entity);
        return entity;

    }


    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redissonService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .dayCount(raffleActivityCount.getDayCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .build();
        redissonService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
    }

    @Override
    public  void doSaveOrder(CreateOrderAggregate createOrderAggregate) {
        try {
            // 订单对象
            ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
            RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
            raffleActivityOrder.setUserId(activityOrderEntity.getUserId());
            raffleActivityOrder.setSku(Long.valueOf(activityOrderEntity.getSku()));
            raffleActivityOrder.setActivityId(activityOrderEntity.getActivityId());
            raffleActivityOrder.setActivityName(activityOrderEntity.getActivityName());
            raffleActivityOrder.setStrategyId(activityOrderEntity.getStrategyId());
            raffleActivityOrder.setOrderId(activityOrderEntity.getOrderId());
            raffleActivityOrder.setOrderTime(activityOrderEntity.getOrderTime());
            raffleActivityOrder.setTotalCount(activityOrderEntity.getTotalCount());
            raffleActivityOrder.setDayCount(activityOrderEntity.getDayCount());
            raffleActivityOrder.setMonthCount(activityOrderEntity.getMonthCount());
            raffleActivityOrder.setTotalCount(createOrderAggregate.getTotalCount());
            raffleActivityOrder.setDayCount(createOrderAggregate.getDayCount());
            raffleActivityOrder.setMonthCount(createOrderAggregate.getMonthCount());
            raffleActivityOrder.setState(activityOrderEntity.getState().getCode());
            raffleActivityOrder.setOutBusinessNo(activityOrderEntity.getOutBusinessNo());
            raffleActivityOrder.setCreateTime(new Date());
            raffleActivityOrder.setUpdateTime(new Date());

            // 账户对象
            RaffleActivityAccount raffleActivityAccount = new RaffleActivityAccount();
            raffleActivityAccount.setUserId(createOrderAggregate.getUserId());
            raffleActivityAccount.setActivityId(createOrderAggregate.getActivityId());
            raffleActivityAccount.setTotalCount(createOrderAggregate.getTotalCount());
            raffleActivityAccount.setTotalCountSurplus(createOrderAggregate.getTotalCount());
            raffleActivityAccount.setDayCount(createOrderAggregate.getDayCount());
            raffleActivityAccount.setDayCountSurplus(createOrderAggregate.getDayCount());
            raffleActivityAccount.setMonthCount(createOrderAggregate.getMonthCount());
            raffleActivityAccount.setMonthCountSurplus(createOrderAggregate.getMonthCount());
            raffleActivityAccount.setCreateTime(new Date());
            raffleActivityAccount.setUpdateTime(new Date());

            dbRouter.doRouter(createOrderAggregate.getUserId());
            transactionTemplate.execute(new TransactionCallback<Integer>() {
                                            @Override
                                            public Integer doInTransaction(TransactionStatus status) {
                                                try {
                                                    // 1. 写入订单
                                                    raffleActivityOrderDao.insert(raffleActivityOrder);
                                                    // 2. 更新账户
                                                    int count = raffleActivityAccountDao.update(raffleActivityAccount);
                                                    // 3. 创建账户 - 更新为0，则账户不存在，创新新账户。
                                                    if (0 == count) {
                                                        raffleActivityAccountDao.insert(raffleActivityAccount);
                                                    }
                                                    return 1;
                                                } catch (DuplicateKeyException e) {
                                                    status.setRollbackOnly();
                                                    log.error("写入订单记录，唯一索引冲突 userId: {} activityId: {} sku: {}", activityOrderEntity.getUserId(), activityOrderEntity.getActivityId(), activityOrderEntity.getSku(), e);
//                    throw new AppException(ResponseCode.INDEX_DUP.getCode());
                                                    throw new RuntimeException();
                                                }

                                            }
                                        }
            );
        }finally {
            dbRouter.clear();
        }

    }




}
