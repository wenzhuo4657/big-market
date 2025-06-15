package cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.impl;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivityStateVO;
import cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.AbstractActionChain;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/18
 * @description: 活动规则过滤【日期、状态】
 */

@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity ) {
        log.info("活动责任链-基础信息【有效期、状态、库存(sku)】校验开始。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        if (!ActivityStateVO.open.equals(activityEntity.getState())){
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(),ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        Date currentDate=new Date();
        if (activityEntity.getBeginDateTime().after(currentDate)||activityEntity.getEndDateTime().before(currentDate)){
            throw  new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(),ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }
        if (activitySkuEntity.getStockCountSurplus()<=0){
            throw  new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(),ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
        }
        return next().action(activitySkuEntity,activityEntity,activityCountEntity);
    }
}
