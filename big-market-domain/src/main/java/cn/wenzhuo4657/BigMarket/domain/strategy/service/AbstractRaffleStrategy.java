package cn.wenzhuo4657.BigMarket.domain.strategy.service;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.ILogicChain;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @className: AbstractRaffleStrategy
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description:
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
    protected IStrategyRepository strategyRepository;
    protected IStrategyDispatch strategyDispatch;
    private final DefaultChainFactory defaultChainFactory;
    protected  final DefaultTreeFactory defaultTreeFactory;


    public AbstractRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        this.strategyRepository = strategyRepository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
        this.defaultTreeFactory = defaultTreeFactory;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        String userId= raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null==strategyId||StringUtils.isBlank(userId)){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }





    }

    public abstract DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId);

    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);
}
