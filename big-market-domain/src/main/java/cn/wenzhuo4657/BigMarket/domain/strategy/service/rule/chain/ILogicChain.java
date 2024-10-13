package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @className: ILogicChain
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description: 逻辑责任链行为定义接口
 */
public interface ILogicChain extends ILogicChainArmory,Cloneable{

    /**
     * 责任链接口
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品对象
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);
}
