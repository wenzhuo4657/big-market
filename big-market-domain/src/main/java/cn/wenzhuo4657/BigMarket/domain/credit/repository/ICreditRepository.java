package cn.wenzhuo4657.BigMarket.domain.credit.repository;

import cn.wenzhuo4657.BigMarket.domain.credit.model.aggregate.TradeAggregate;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditAccountEntity;

import java.math.BigDecimal;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description: 积分领域仓储
 */
public interface ICreditRepository {
    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);

    CreditAccountEntity queryUserCreditAccount(String userId);

    void updateCreditAccount(BigDecimal multiply, String userId);
}
