package cn.wenzhuo4657.BigMarket.domain.activity.service.quota;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.*;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.OrderTradeTypeVO;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivityAccountQuotaService;

import cn.wenzhuo4657.BigMarket.domain.activity.service.quota.policy.ITradePolicy;
import cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.IActionChain;
import cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;

import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
@Slf4j
public abstract class AbstractRaffleActivityQuota extends RaffleActivityQuotaSupport implements IRaffleActivityAccountQuotaService {


    private  Map<String, ITradePolicy> tradePolicyGroup;

    public AbstractRaffleActivityQuota(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory, Map<String, ITradePolicy> tradePolicyGroup) {
        super(defaultActivityChainFactory,activityRepository);
        this.tradePolicyGroup = tradePolicyGroup;
    }






    @Override
    public UnpaidActivityOrderEntity createSkuRechargeOrder( SkuRechargeEntity skuRechargeEntity) {
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();

        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }


//        1，当需要创建的订单是有支付订单时，查询是否存在当前充值的sku商品是否存在未支付订单,如果存在直接返回
        UnpaidActivityOrderEntity unpaidCreditOrder =activityRepository.queryUnpaidActivityOrder(skuRechargeEntity);
        if (skuRechargeEntity.getOrderTradeType()  == OrderTradeTypeVO.credit_pay_trade &&null!=unpaidCreditOrder) return unpaidCreditOrder;

//        2，创建订单。
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());


//        责任链，1，拦截报异常 2，未拦截则会在库存节点中扣减sku商品库存
        IActionChain iActionChain = defaultActivityChainFactory.openActionChain();
        iActionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        CreateQuotaOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);

//        使用交易策略模块完成充值订单加载、扣减
        ITradePolicy tradePolicy = tradePolicyGroup.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createOrderAggregate);
        ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
        return UnpaidActivityOrderEntity.builder()
                .userId(userId)
                .orderId(activityOrderEntity.getOrderId())
                .outBusinessNo(activityOrderEntity.getOutBusinessNo())
                .payAmount(activityOrderEntity.getPayAmount())
                .build();
    }

    /**
     *  @author:wenzhuo4657
        des: 创建订单
    */
    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

}
