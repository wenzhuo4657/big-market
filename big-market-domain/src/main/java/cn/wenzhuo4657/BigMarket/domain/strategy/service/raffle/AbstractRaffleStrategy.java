package cn.wenzhuo4657.BigMarket.domain.strategy.service.raffle;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory.DefaultLogicFactory;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;

/**
 * @className: AbstractRaffleStrategy
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description:
 */
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
    protected IStrategyRepository strategyRepository;
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch) {
        this.strategyRepository = strategyRepository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        String userId= raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null==strategyId||StringUtils.isBlank(userId)){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);



//        1,执行抽奖前策略，
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> raffleBeforeEntityRuleActionEntity =
                this.doCheckRaffleBeforeLogic(new RaffleFactorEntity(userId, strategyId), strategyEntity.ruleModels());


        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleBeforeEntityRuleActionEntity.getCode())){
            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.equals(raffleBeforeEntityRuleActionEntity.getRuleModel())){
                return RaffleAwardEntity.builder().awardId(raffleBeforeEntityRuleActionEntity.getData().getAwardId())
                        .build();

            }else if (DefaultLogicFactory.LogicModel.RULE_WIGHT.equals(raffleBeforeEntityRuleActionEntity.getRuleModel())){
                RuleActionEntity.RaffleBeforeEntity data = raffleBeforeEntityRuleActionEntity.getData();
                String ruleWeightValueKey = data.getRuleWeightValueKey();
                Integer awarId=strategyDispatch.getRandomAwardId(data.getStrategyId(),data.getRuleWeightValueKey());
                return RaffleAwardEntity.builder()
                        .awardId(awarId)
                        .build();
            }

        }


//        end，如果没有任何策略命中，则使用默认抽奖行为，即第一种概率装配:策略概率表装配
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);

        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }

    /**
     *  @author:wenzhuo4657
        des: 进行抽奖前过滤并返回结果。
    */
    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}
