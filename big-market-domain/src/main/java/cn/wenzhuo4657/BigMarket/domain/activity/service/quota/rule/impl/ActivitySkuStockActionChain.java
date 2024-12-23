package cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.impl;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityCountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.domain.activity.service.armory.IActivityArmory;
import cn.wenzhuo4657.BigMarket.domain.activity.service.armory.IActivityDispatch;
import cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.AbstractActionChain;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/18
 * @description: 商品库存规则节点
 */
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {
    @Resource
    private IActivityDispatch activityDispatch;
    @Resource
    private IActivityArmory activityArmory;
    @Resource
    private IActivityRepository activityRepository;
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】开始。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        activityArmory.assembleActivitySku(activitySkuEntity.getSku());
        boolean status = activityDispatch.subtractionActivitySkuStock(activitySkuEntity.getSku(), activityEntity.getEndDateTime());

        if (status){
            log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】成功。sku:{} activityId:{}",activitySkuEntity.getSku(),activitySkuEntity.getActivityId());

            activityRepository.activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO.builder()
                    .sku(activitySkuEntity.getSku())
                    .activityId(activityEntity.getActivityId())
                    .build());
            return true;
        }
        throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
    }

  //  wenzhuo TODO 2024/12/15 : 积分购买流程中，关于积分的正负扣减流程
  //  wenzhuo TODO 2024/12/15 : 在前端中增加预热活动按钮
}
