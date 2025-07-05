package cn.wenzhuo4657.LuckySphere.domain.activity.service.quota.policy.impl;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.valobj.OrderStateVO;
import cn.wenzhuo4657.LuckySphere.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.quota.policy.ITradePolicy;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import org.springframework.stereotype.Service;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/11
 * @description:  积分兑换，支付类订单
 */
@Service("credit_pay_trade")
public class CreditPayTradePolicy implements ITradePolicy {
    private final IActivityRepository activityRepository;
    public CreditPayTradePolicy(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {

        CreditAccountEntity creditAccountEntity = activityRepository.queryUserCreditAccount(createQuotaOrderAggregate.getUserId());

        if (creditAccountEntity.getAdjustAmount().compareTo(createQuotaOrderAggregate.getActivityOrderEntity().getPayAmount()) < 0) {
            throw new AppException("积分不足");
        }


        createQuotaOrderAggregate.setOrderState(OrderStateVO.wait_pay);
        activityRepository.doSaveCreditPayOrder(createQuotaOrderAggregate);
    }
}
