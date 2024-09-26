package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.annotation.LogicStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.ILogicFilter;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * @className: RuleWeightLogicFilter
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description: 权重校验。
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {
    private IStrategyRepository repository;
    public  Long userScore=4500L;

    public RuleWeightLogicFilter(IStrategyRepository repository) {
        this.repository = repository;
    }

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-权重范围 userId:{} strategyId:{} ruleModel:{}",ruleMatterEntity.getUserId(),ruleMatterEntity.getStrategyId(),ruleMatterEntity.getRuleModel());
        String userId=ruleMatterEntity.getUserId();
        Long stategyId=ruleMatterEntity.getStrategyId();
        String ruleValue = repository.queryStrategyRuleValue(stategyId, ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());

        return null;
    }
}
