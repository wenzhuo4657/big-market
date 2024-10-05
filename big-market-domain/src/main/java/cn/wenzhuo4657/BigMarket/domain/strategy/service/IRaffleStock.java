package cn.wenzhuo4657.BigMarket.domain.strategy.service;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * @className: IRaffleStock
 * @author: wenzhuo4657
 * @date: 2024/10/5
 * @Version: 1.0
 * @description:  抽奖库存相关服务
 */
public interface IRaffleStock {
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;
    void updateStrategyAwardStock(Long strategyId, Integer awardId);
}
