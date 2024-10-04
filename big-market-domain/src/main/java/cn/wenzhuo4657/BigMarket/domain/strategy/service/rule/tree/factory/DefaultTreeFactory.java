package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleTreeVo;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @className: DefaultTreeFactory
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description: 默认规则树工厂
 */
@Service
public class DefaultTreeFactory{


    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    @Autowired
    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }
    public IDecisionTreeEngine openLogicTree(RuleTreeVo ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }

    /**
     * 决策树个动作实习
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardData strategyAwardData;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardData {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }
}
