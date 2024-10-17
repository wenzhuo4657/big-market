package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivityStateVO;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.RaffleActivityCountDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.RaffleActivityDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.RaffleActivitySkuDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivity;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityCount;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivitySku;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.IRedisService;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 活动仓储服务
 */
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redissonService;
    @Resource
    private RaffleActivityDao raffleActivityDao;
    @Resource
    private RaffleActivitySkuDao raffleActivitySkuDao;
    @Resource
    private RaffleActivityCountDao raffleActivityCountDao;
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
}
