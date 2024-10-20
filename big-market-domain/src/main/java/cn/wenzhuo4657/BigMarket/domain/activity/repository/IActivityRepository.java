package cn.wenzhuo4657.BigMarket.domain.activity.repository;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 活动仓储接口
 */
public interface IActivityRepository {
    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    /**
     *  @author:wenzhuo4657
        des: 聚合事务，创建订单
    */
    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

    /**
     *  @author:wenzhuo4657
        des: redis缓存：如果不存在对应key，则缓存
    */
    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    ActivitySkuStockKeyVO takeQueueValue();

    void clearQueueValue();

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);
}
