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

    /**
     *  @author:wenzhuo4657
        des: 扣减对应sku库存
    */

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    /**
     *  @author:wenzhuo4657
        des: 推送消费到redis延迟队列中，（该队列用于更新库存到mysql中）
    */
    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    /**
     *  @author:wenzhuo4657
        des: 获取消费队列头部，并使用泛型约束其取出的类型
    */
    ActivitySkuStockKeyVO takeQueueValue();

    /**
     *  @author:wenzhuo4657
        des: 清空redis中消费队列
    */
    void clearQueueValue();

    /**
     *  @author:wenzhuo4657
        des: 更新mysql库存，实际上时库存-1，并更新updata-time字段。
    */
    void updateActivitySkuStock(Long sku);

    /**
     *  @author:wenzhuo4657
        des: 清空库存。
    */
    void clearActivitySkuStock(Long sku);
}
