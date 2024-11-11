package cn.wenzhuo4657.BigMarket.domain.activity.repository;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.*;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;
import java.util.List;

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
        des: 更新mysql库存，实际上是库存-1，并更新updata-time字段。
    */
    void updateActivitySkuStock(Long sku);

    /**
     *  @author:wenzhuo4657
        des: 清空库存。
    */
    void clearActivitySkuStock(Long sku);


    /**
     *  @author:wenzhuo4657
        des: 查询参与活动订单，
     该订单实际上代表成功参加活动，如果有则可以继续向下执行，否则不可以。
    */
    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);

    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);

    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);

    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);

    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);


    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);

    Integer queryRaffleActivityAccountPartakeCount(String userId, Long activityId);
/**
 *  @author:wenzhuo4657
    des: 查询用户活动次数实体，且注意会先去查询活动账户，如果没有对应活动，则不会去查询个人账户，直接返回全为0的实体。
 着实际上和参入活动的流程是一样的，同样是先从活动账户扣减，然后再扣减个人账户
*/
    ActivityAccountEntity queryActivityAccountEntity(Long activityId, String userId);

    void updateOrder(DeliveryOrderEntity deliveryOrderEntity);

    void doSaveNoPayOrder(CreateQuotaOrderAggregate createOrderAggregate);

    void doSaveCreditPayOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);
}
