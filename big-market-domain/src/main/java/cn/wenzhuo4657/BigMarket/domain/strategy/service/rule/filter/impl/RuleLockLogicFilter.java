package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.filter.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.annotation.LogicStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.filter.ILogicFilter;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @className: RuleLockLogicFilter
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description:
 * 该规则表示如果某些商品只有在抽奖到一定次数之后才可以解锁，
 * 代码逻辑中表现为，如果未超过一定次数，就受到规则管制，将奖品从奖品池子中删除。
 *  而并非是次数到了一定程度，向奖品池子增加某奖品。。
 */

@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_LOCK)
public class RuleLockLogicFilter implements ILogicFilter<RuleActionEntity.RaffleCenterEntity> {

    private  Long userRaffleCount=0l;
    private IStrategyRepository strategyRepository;

    public RuleLockLogicFilter(IStrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleCenterEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-次数锁 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String ruleValue = strategyRepository.
                queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        long raffleCount = Long.parseLong(ruleValue);

        if (userRaffleCount>=raffleCount){
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                .build();
    }
}
