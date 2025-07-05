package cn.wenzhuo4657.LuckySphere.domain.activity.service.quota;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.*;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.valobj.OrderStateVO;
import cn.wenzhuo4657.LuckySphere.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.IRaffleActivitySkuStockService;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.quota.policy.ITradePolicy;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import cn.wenzhuo4657.LuckySphere.types.utils.RandomOrderIdUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivityQuota implements IRaffleActivitySkuStockService {



    public RaffleActivityService(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory, Map<String, ITradePolicy> tradePolicyGroup) {
        super(activityRepository, defaultActivityChainFactory, tradePolicyGroup);
    }

    @Override
    protected CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        ActivityOrderEntity activityOrderEntity = new ActivityOrderEntity();
        activityOrderEntity.setUserId(skuRechargeEntity.getUserId());
        activityOrderEntity.setSku(skuRechargeEntity.getSku());
        activityOrderEntity.setActivityId(activityEntity.getActivityId());
        activityOrderEntity.setActivityName(activityEntity.getActivityName());
        activityOrderEntity.setStrategyId(activityEntity.getStrategyId());


        activityOrderEntity.setOrderId(RandomOrderIdUtils.getOrderIdByTime());
        activityOrderEntity.setOrderTime(new Date());
        activityOrderEntity.setTotalCount(activityCountEntity.getTotalCount());
        activityOrderEntity.setDayCount(activityCountEntity.getDayCount());
        activityOrderEntity.setMonthCount(activityCountEntity.getMonthCount());
        activityOrderEntity.setPayAmount(activitySkuEntity.getProductAmount());
        activityOrderEntity.setOutBusinessNo(skuRechargeEntity.getOutBusinessNo());

        return CreateQuotaOrderAggregate.builder()
                .activityOrderEntity(activityOrderEntity)
                .activityId(activityEntity.getActivityId())
                .userId(skuRechargeEntity.getUserId())
                .monthCount(activityCountEntity.getMonthCount())
                .dayCount(activityCountEntity.getDayCount())
                .totalCount(activityCountEntity.getTotalCount())
                .build();
    }



    @Override
    public ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException {
        return activityRepository.takeQueueValue();
    }

    @Override
    public void clearQueueValue() {
        activityRepository.clearQueueValue();
    }

    @Override
    public void updateActivitySkuStock(Long sku) {
        activityRepository.updateActivitySkuStock(sku);
    }

    @Override
    public void clearActivitySkuStock(Long sku) {
        activityRepository.clearActivitySkuStock(sku);
    }

    @Override
    public Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId) {
        return activityRepository.queryRaffleActivityAccountDayPartakeCount(activityId, userId);
    }

    @Override
    public Integer queryRaffleActivityAccountPartakeCount(String userId, Long activityId) {
        return activityRepository.queryRaffleActivityAccountPartakeCount(userId,activityId) ;
    }


    //            由于在并发情况下允许创建订单，所以对于账户额度查询，应该加上当先未使用的抽奖订单，
//            且注意，由于抽奖订单是分活动进行的，所以即便用户抽奖的配置为0，但是存在未使用抽奖订单也可以进行抽奖行为。
    @Override
    public ActivityAccountEntity queryActivityAccountEntity(Long activityId, String userId) {
        int noUsedRaffleOrderSize = activityRepository.queryNoUsedRaffleOrderSize(new PartakeRaffleActivityEntity(userId, activityId));
        ActivityAccountEntity activityAccountEntity = activityRepository.queryActivityAccountEntity(activityId, userId);

        activityAccountEntity.setTotalCountSurplus(activityAccountEntity.getTotalCount() +noUsedRaffleOrderSize);
        activityAccountEntity.setDayCountSurplus(activityAccountEntity.getDayCount() +noUsedRaffleOrderSize);
        activityAccountEntity.setMonthCountSurplus(activityAccountEntity.getMonthCount() +noUsedRaffleOrderSize);
        activityAccountEntity.setTotalCount(activityAccountEntity.getTotalCount() +noUsedRaffleOrderSize);
        activityAccountEntity.setDayCount(activityAccountEntity.getDayCount() +noUsedRaffleOrderSize);
        activityAccountEntity.setMonthCount(activityAccountEntity.getMonthCount() +noUsedRaffleOrderSize);

        return  activityAccountEntity;
    }

    @Override
    public Integer queryRaffleActivityAccountPartakeCount(Long activityId, String userId) {
        return activityRepository.queryRaffleActivityAccountDayPartakeCount(activityId, userId);
    }

    @Override
    public void updateOrder(DeliveryOrderEntity deliveryOrderEntity) {
        activityRepository.updateOrder(deliveryOrderEntity);
    }
}
