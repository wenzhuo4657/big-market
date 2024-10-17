package cn.wenzhuo4657.BigMarket.domain.activity.service;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.*;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
@Slf4j
public abstract class AbstractRaffleActivity implements  IRaffleOrder {
    protected IActivityRepository activityRepository;

    public AbstractRaffleActivity(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        ActivitySkuEntity activitySkuEntity =
                activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        ActivityEntity activityEntity =
                activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        ActivityCountEntity activityCountEntity =
                activityRepository.queryRaffleActivityCountByActivityCountId(activityEntity.getActivityId());
        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

          //  wenzhuo TODO 2024/10/17 : 这里并没有进行填充，
        return ActivityOrderEntity.builder().build();
    }
}
