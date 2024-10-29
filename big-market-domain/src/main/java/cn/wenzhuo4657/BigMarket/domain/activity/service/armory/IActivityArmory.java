package cn.wenzhuo4657.BigMarket.domain.activity.service.armory;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/21
 * @description: 活动装配接口
 */
public interface IActivityArmory {

    /**
     *  @author:wenzhuo4657
            1，缓存键值对到redis中
    */
    boolean assembleActivitySku(Long sku);


    boolean assembleActivitySkuByActivityId(Long activityId);
}
