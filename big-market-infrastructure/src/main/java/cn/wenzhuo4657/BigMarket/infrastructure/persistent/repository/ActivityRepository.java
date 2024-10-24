package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.wenzhuo4657.BigMarket.domain.activity.evnet.ActivitySkuStockZeroMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.*;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivityStateVO;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.UserRaffleOrderStateVO;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.event.EventPublisher;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.*;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.*;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.IRedisService;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    private RaffleActivityAccountMonthDao raffleActivityAccountMonthDao;
    @Resource
    private  RaffleActivityAccountDayDao raffleActivityAccountDayDao;

    @Resource
    private UserRaffleOrderDao userRaffleOrderDao;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private ActivitySkuStockZeroMessageEvent activitySkuStockZeroMessageEvent;
    @Resource
    private EventPublisher eventPublisher;


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
    public  void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate) {
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

    @Override
    public void cacheActivitySkuStockCount(String cacheKey, Integer stockCount) {
        if (redissonService.isExists(cacheKey)) return;
        redissonService.setAtomicLong(cacheKey,stockCount);
    }

    //  wenzhuo TODO 2024/10/20 : 加锁是为了兜底，并没有自动恢复库存的方式，相当于备份消费记录。
    @Override
    public boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime) {
        Long surplus=redissonService.decr(cacheKey);
        if (surplus==0){
         /**
             *  @author:wenzhuo4657
                des: 使用MQ推送消息，通知sku商品活动缓存中库存为0
            */
            eventPublisher.publish(activitySkuStockZeroMessageEvent.topic(),activitySkuStockZeroMessageEvent.buildEventMessage(sku));
            return  false;
        } else if (surplus<0) {
            redissonService.setAtomicLong(cacheKey,0);
            return  false;
        }

        String lockKey=cacheKey+Constants.UNDERLINE+surplus;
        long expireMillis = endDateTime.getTime() - System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
        Duration of = Duration.of(expireMillis, ChronoUnit.DAYS);
        Boolean aBoolean = redissonService.setNx(lockKey, of);
        if (!aBoolean){
            log.info("活动sku库存加锁失败 {}", lockKey);
        }
        return true;
    }
  //  wenzhuo TODO 2024/10/21 :  redis队列
    @Override
    public void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO) {
        String cacheKey=Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        RBlockingQueue<Object> blockingQueue = redissonService.getBlockingQueue(cacheKey);
        RDelayedQueue<Object> delayedQueue = redissonService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(activitySkuStockKeyVO,3,TimeUnit.SECONDS);
    }

    @Override
    public ActivitySkuStockKeyVO takeQueueValue() {
        /**
         *  @author:wenzhuo4657
            des:
         注意泛型的使用，此处泛型只能控制出，而不能管理进入，又或者说，获取该泛型时队列中已经存在不受控制的元素，
         此处的泛型约束仅仅起到约束取出的作用。
        */
        String cacheKey=Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        RBlockingQueue<ActivitySkuStockKeyVO> blockingQueue = redissonService.getBlockingQueue(cacheKey);
        return blockingQueue.poll();
    }

    @Override
    public void clearQueueValue() {
        String cacheKey=Constants.RedisKey.ACTIVITY_SKU_COUNT_QUERY_KEY;
        RBlockingQueue<Object> blockingQueue = redissonService.getBlockingQueue(cacheKey);
        blockingQueue.clear();
    }

    @Override
    public void updateActivitySkuStock(Long sku) {
        raffleActivitySkuDao.updateActivitySkuStock(sku);
    }

    @Override
    public void clearActivitySkuStock(Long sku) {
        raffleActivitySkuDao.clearActivitySkuStock(sku);
    }

    @Override
    public UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        UserRaffleOrder userRaffleOrderReq = new UserRaffleOrder();
        userRaffleOrderReq.setUserId(partakeRaffleActivityEntity.getUserId());
        userRaffleOrderReq.setActivityId(partakeRaffleActivityEntity.getActivityId());
        UserRaffleOrder userRaffleOrderRes = userRaffleOrderDao.queryNoUsedRaffleOrder(userRaffleOrderReq);
        if (null == userRaffleOrderRes) return null;
        UserRaffleOrderEntity userRaffleOrderEntity = new UserRaffleOrderEntity();
        userRaffleOrderEntity.setUserId(userRaffleOrderRes.getUserId());
        userRaffleOrderEntity.setActivityId(userRaffleOrderRes.getActivityId());
        userRaffleOrderEntity.setActivityName(userRaffleOrderRes.getActivityName());
        userRaffleOrderEntity.setStrategyId(userRaffleOrderRes.getStrategyId());
        userRaffleOrderEntity.setOrderId(userRaffleOrderRes.getOrderId());
        userRaffleOrderEntity.setOrderTime(userRaffleOrderRes.getOrderTime());
        userRaffleOrderEntity.setOrderState(UserRaffleOrderStateVO.valueOf(userRaffleOrderRes.getOrderState()));
        return userRaffleOrderEntity;
    }

    @Override
    public ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId) {
        RaffleActivityAccount raffleActivityAccountReq = new RaffleActivityAccount();
        raffleActivityAccountReq.setUserId(userId);
        raffleActivityAccountReq.setActivityId(activityId);
        RaffleActivityAccount raffleActivityAccountRes = raffleActivityAccountDao.queryActivityAccountByUserId(raffleActivityAccountReq);
        if (null == raffleActivityAccountRes) return null;
        // 2. 转换对象
        return ActivityAccountEntity.builder()
                .userId(raffleActivityAccountRes.getUserId())
                .activityId(raffleActivityAccountRes.getActivityId())
                .totalCount(raffleActivityAccountRes.getTotalCount())
                .totalCountSurplus(raffleActivityAccountRes.getTotalCountSurplus())
                .dayCount(raffleActivityAccountRes.getDayCount())
                .dayCountSurplus(raffleActivityAccountRes.getDayCountSurplus())
                .monthCount(raffleActivityAccountRes.getMonthCount())
                .monthCountSurplus(raffleActivityAccountRes.getMonthCountSurplus())
                .build();
    }

    @Override
    public ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month) {
        RaffleActivityAccountMonth raffleActivityAccountMonthReq = new RaffleActivityAccountMonth();
        raffleActivityAccountMonthReq.setUserId(userId);
        raffleActivityAccountMonthReq.setActivityId(activityId);
        raffleActivityAccountMonthReq.setMonth(month);
        RaffleActivityAccountMonth raffleActivityAccountMonthRes = raffleActivityAccountMonthDao.queryActivityAccountMonthByUserId(raffleActivityAccountMonthReq);
        if (null == raffleActivityAccountMonthRes) return null;
        // 2. 转换对象
        return ActivityAccountMonthEntity.builder()
                .userId(raffleActivityAccountMonthRes.getUserId())
                .activityId(raffleActivityAccountMonthRes.getActivityId())
                .month(raffleActivityAccountMonthRes.getMonth())
                .monthCount(raffleActivityAccountMonthRes.getMonthCount())
                .monthCountSurplus(raffleActivityAccountMonthRes.getMonthCountSurplus())
                .build();

    }

    @Override
    public ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day) {
        RaffleActivityAccountDay raffleActivityAccountDayReq = new RaffleActivityAccountDay();
        raffleActivityAccountDayReq.setUserId(userId);
        raffleActivityAccountDayReq.setActivityId(activityId);
        raffleActivityAccountDayReq.setDay(day);
        RaffleActivityAccountDay raffleActivityAccountDayRes = raffleActivityAccountDayDao.queryActivityAccountDayByUserId(raffleActivityAccountDayReq);
        if (null == raffleActivityAccountDayRes) return null;
        // 2. 转换对象
        return ActivityAccountDayEntity.builder()
                .userId(raffleActivityAccountDayRes.getUserId())
                .activityId(raffleActivityAccountDayRes.getActivityId())
                .day(raffleActivityAccountDayRes.getDay())
                .dayCount(raffleActivityAccountDayRes.getDayCount())
                .dayCountSurplus(raffleActivityAccountDayRes.getDayCountSurplus())
                .build();

    }

    @Override
    public void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate) {
        String userId = createPartakeOrderAggregate.getUserId();
        Long activityId = createPartakeOrderAggregate.getActivityId();
        ActivityAccountEntity activityAccountEntity = createPartakeOrderAggregate.getActivityAccountEntity();
        ActivityAccountMonthEntity activityAccountMonthEntity = createPartakeOrderAggregate.getActivityAccountMonthEntity();
        ActivityAccountDayEntity activityAccountDayEntity = createPartakeOrderAggregate.getActivityAccountDayEntity();
        UserRaffleOrderEntity userRaffleOrderEntity = createPartakeOrderAggregate.getUserRaffleOrderEntity();


    }
}
