package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyArmory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.AbstractLogicChain;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import lombok.extern.apachecommons.CommonsLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @className: DefaultLogicChain
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description: 默认抽奖
 */
@Slf4j
@Component("rule_default")
public class DefaultLogicChain extends AbstractLogicChain {


    protected IStrategyDispatch strategyDispatch;

    public DefaultLogicChain(IStrategyDispatch strategyDispatch) {
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    protected String ruleModel() {
        return  DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardVO.builder().awardId(awardId).logicModel(DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode()).build();
    }

}
