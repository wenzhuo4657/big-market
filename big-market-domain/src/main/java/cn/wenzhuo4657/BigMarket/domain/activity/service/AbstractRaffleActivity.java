package cn.wenzhuo4657.BigMarket.domain.activity.service;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.*;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.domain.activity.service.rule.IActionChain;
import cn.wenzhuo4657.BigMarket.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
@Slf4j
public abstract class AbstractRaffleActivity extends RaffleActivitySupport implements  IRaffleOrder {

    public AbstractRaffleActivity(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        super(defaultActivityChainFactory, activityRepository);
    }


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

    @Override
    public String createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity) {
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

          //  wenzhuo TODO 2024/10/19 : 暂时不处理责任链结果
        IActionChain iActionChain = defaultActivityChainFactory.openActionChain();
        boolean res = iActionChain.action(activitySkuEntity, activityEntity, activityCountEntity);
        CreateOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);
        doSaveOrder(createOrderAggregate);
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }

    /**
     *  @author:wenzhuo4657
        des: 创建订单
    */
    protected abstract CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

    /**
     *  @author:wenzhuo4657
        des: 消费订单
    */
    protected abstract void doSaveOrder(CreateOrderAggregate createOrderAggregate);
}
