package cn.wenzhuo4657.LuckySphere.domain.credit.service;

import cn.wenzhuo4657.LuckySphere.domain.credit.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.entity.TradeEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description: 积分调整服务
 */

public interface ICreditAdjustService {
    /**
     * 创建增加积分额度订单/账户充值订单
     * @param tradeEntity 交易实体对象
     * @return 积分订单id（积分订单不需要消费直接入库就可以）
     */
    String createOrder(TradeEntity tradeEntity);


    /**
     * 查询用户积分账户
     * @param userId 用户ID
     * @return 积分账户实体
     */
    CreditAccountEntity queryUserCreditAccount(String userId);

    void saveIntegralRebateOrder(TradeEntity tradeEntity);
}
