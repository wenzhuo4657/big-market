package cn.wenzhuo4657.BigMarket.domain.activity.service.quota.policy;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateQuotaOrderAggregate;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/11
 * @description: 交易策略接口
 */
public interface ITradePolicy {
    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);
}
