package cn.wenzhuo4657.LuckySphere.domain.activity.service.quota;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/18
 * @description:
 */
public class RaffleActivityQuotaSupport {
    protected DefaultActivityChainFactory defaultActivityChainFactory;
    protected IActivityRepository activityRepository;

    public RaffleActivityQuotaSupport(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        this.defaultActivityChainFactory = defaultActivityChainFactory;
        this.activityRepository = activityRepository;
    }
    public ActivitySkuEntity queryActivitySku(Long sku) {
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }


}
