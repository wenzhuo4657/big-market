package cn.wenzhuo4657.LuckySphere.domain.activity.service;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.SkuProductEntity;

import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/12
 * @description: sku商品相关服务（目前仅支持使用积分交易）
 */

public interface IRaffleActivitySkuProductService {


    /**
     * 查询当前活动ID下，创建的 sku 商品。「sku可以兑换活动抽奖次数」
     * @param activityId 活动ID
     * @return 返回sku商品集合
     */
    List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId);
}
