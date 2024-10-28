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

    /**
     *  @author:wenzhuo4657
        des: 获取延迟队列头部
    */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     *  @author:wenzhuo4657
        des: mysql中自减库存
    */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);
}
