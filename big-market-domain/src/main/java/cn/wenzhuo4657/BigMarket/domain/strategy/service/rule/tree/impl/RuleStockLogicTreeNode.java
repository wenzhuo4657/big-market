package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: RuleStockLogicTreeNode
 * @author: wenzhuo4657
 * @date: 2024/10/4
 * @Version: 1.0
 * @description:  库存扣减节点
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyDispatch strategyDispatch;
    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-库存扣减 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        strategyDispatch.sub
        return null;
    }
}
