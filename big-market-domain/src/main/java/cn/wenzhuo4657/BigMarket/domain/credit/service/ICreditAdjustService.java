package cn.wenzhuo4657.BigMarket.domain.credit.service;

import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.TradeEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description:
 */

public interface ICreditAdjustService {
    /**
     * 创建增加积分额度订单
     * @param tradeEntity 交易实体对象
     * @return 单号
     */
    String createOrder(TradeEntity tradeEntity);

}
