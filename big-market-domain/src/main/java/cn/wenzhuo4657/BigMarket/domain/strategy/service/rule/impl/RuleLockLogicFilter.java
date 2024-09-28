package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.annotation.LogicStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.ILogicFilter;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory.DefaultLogicFactory;
import org.springframework.stereotype.Component;

/**
 * @className: RuleLockLogicFilter
 * @author: wenzhuo4657
 * @date: 2024/9/28
 * @Version: 1.0
 * @description: 抽奖次数过滤
 */
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel)
public class RuleLockLogicFilter implements ILogicFilter<RuleActionEntity.RaffleCenterEntity> {
    private IStrategyRepository repository;
    /**
     *  @author:wenzhuo4657
        des: 暂时表示抽奖此处，后续实现数据查询。
    */
    private Long userRaffleCount = 0L;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleCenterEntity> filter(RuleMatterEntity ruleMatterEntity) {

        return null;
    }
}
