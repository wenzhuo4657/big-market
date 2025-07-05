package cn.wenzhuo4657.LuckySphere.domain.strategy.service;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.RaffleAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.raffle.DefaultRaffleStrategy;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
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
    protected final DefaultChainFactory defaultChainFactory;
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
        DefaultChainFactory.StrategyAwardVO chainStrategyAwardVO=raffleLogicChain(userId,strategyId);
        log.info("抽奖策略计算-责任链 {} {} {} {}", userId, strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getLogicModel());
        /**
         *  @author:wenzhuo4657
            des:非默认抽奖直接返回结果，
        */
        if (!DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode().equals(chainStrategyAwardVO.getLogicModel())){
            return buildRaffleAwardEntity(strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getAwardRuleValue());
        }
        Integer awardId = chainStrategyAwardVO.getAwardId();
        DefaultTreeFactory.StrategyAwardVO treeStrategyAwardVO = raffleLogicTree(userId, strategyId, awardId);
        log.info("抽奖策略计算-规则树 {} {} {} {}", userId, strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());

        return buildRaffleAwardEntity(strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());
    }

    /**
     *  @author:wenzhuo4657
        des: 统一响应格式
    */
    private RaffleAwardEntity buildRaffleAwardEntity(Long strategyId, Integer awardId, String awardConfig) {
        StrategyAwardEntity strategyAward = strategyRepository.queryStrategyAwardEntity(strategyId, awardId);
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .awardTitle(strategyAward.getAwardTitle())
                .awardConfig(awardConfig)
                .sort(strategyAward.getSort())

                .build();
    }

    /**
     *  @author:wenzhuo4657
        des:责任链抽奖
    */
    public abstract DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId);

    /**
     *  @author:wenzhuo4657
        des:规则树过滤
    */
    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);
}
