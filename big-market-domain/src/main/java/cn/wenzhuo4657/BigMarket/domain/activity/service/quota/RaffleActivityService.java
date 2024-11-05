package cn.wenzhuo4657.BigMarket.domain.activity.service.quota;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.*;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.OrderStateVO;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivitySkuStockService;
import cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivityQuota implements IRaffleActivitySkuStockService {


    public RaffleActivityService(DefaultActivityChainFactory defaultActivityChainFactory, IActivityRepository activityRepository) {
        super(defaultActivityChainFactory, activityRepository);
    }

    @Override
    protected CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        ActivityOrderEntity activityOrderEntity = new ActivityOrderEntity();
        activityOrderEntity.setUserId(skuRechargeEntity.getUserId());
        activityOrderEntity.setSku(skuRechargeEntity.getSku());
        activityOrderEntity.setActivityId(activityEntity.getActivityId());
        activityOrderEntity.setActivityName(activityEntity.getActivityName());
        activityOrderEntity.setStrategyId(activityEntity.getStrategyId());

        /**
         *  @author:wenzhuo4657
            des: 订单编号不需要唯一，幂等是通过outBusinessNo字段实现的。数据库中有相应的约束
        */
        activityOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        activityOrderEntity.setOrderTime(new Date());
        activityOrderEntity.setTotalCount(activityCountEntity.getTotalCount());
        activityOrderEntity.setDayCount(activityCountEntity.getDayCount());
        activityOrderEntity.setMonthCount(activityCountEntity.getMonthCount());
        activityOrderEntity.setState(OrderStateVO.completed);
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
    protected void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate) {
        activityRepository.doSaveOrder(createOrderAggregate);
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
}
