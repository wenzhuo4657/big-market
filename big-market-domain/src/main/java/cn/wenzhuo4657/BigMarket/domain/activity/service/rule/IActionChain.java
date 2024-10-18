package cn.wenzhuo4657.BigMarket.domain.activity.service.rule;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 活动过滤链执行接口定义
 */
public interface IActionChain  extends  IActionChainArmory{
    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
