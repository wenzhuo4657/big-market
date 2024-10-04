package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @className: ILogicTreeNode
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description: 规则节点执行接口定义
 */
public interface ILogicTreeNode {
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);
}
