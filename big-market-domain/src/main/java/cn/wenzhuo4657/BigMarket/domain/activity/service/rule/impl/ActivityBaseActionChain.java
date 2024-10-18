package cn.wenzhuo4657.BigMarket.domain.activity.service.rule.impl;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/18
 * @description: 活动规则过滤【日期、状态】
 */

@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-基础信息【有效期、状态】校验开始。");

        return next().action(activitySkuEntity,activityEntity,activityCountEntity);
    }
}
