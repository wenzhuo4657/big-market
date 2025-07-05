package cn.wenzhuo4657.LuckySphere.domain.activity.service.armory;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/21
 * @description: 活动装配接口
 */
public interface IActivityArmory {


    boolean assembleActivitySku(Long sku);


    /**
     * @Author wenzhuo4657
     * @param  活动id
     * @return  缓存sku商品列表以及对应活动账户次数
     */
    boolean assembleActivitySkuByActivityId(Long activityId);
}
