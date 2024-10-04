package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.engine;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @className: IDecisionTreeEngine
 * @author: wenzhuo4657
 * @date: 2024/10/4
 * @Version: 1.0
 * @description: 装配规则树引擎接口定义
 */
public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);
}
