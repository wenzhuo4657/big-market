package cn.wenzhuo4657.BigMarket.domain.strategy.service.raffle;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
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
                this.doCheckRaffleBeforeLogic(RaffleFactorEntity.builder()
                                                                    .userId(userId)
                                                                    .strategyId(strategyId).build()
                        , strategyEntity.ruleModels());


        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleBeforeEntityRuleActionEntity.getCode())){

            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(raffleBeforeEntityRuleActionEntity.getRuleModel())){
                return RaffleAwardEntity.builder().awardId(raffleBeforeEntityRuleActionEntity.getData().getAwardId())
                        .build();

            }else if (DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(raffleBeforeEntityRuleActionEntity.getRuleModel())){
                RuleActionEntity.RaffleBeforeEntity data = raffleBeforeEntityRuleActionEntity.getData();
                String ruleWeightValueKey = data.getRuleWeightValueKey();
                Integer awarId=strategyDispatch.getRandomAwardId(strategyId,ruleWeightValueKey);
                return RaffleAwardEntity.builder()
                        .awardId(awarId)
                        .build();
            }

        }


//      2.1,抽奖中
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);


        StrategyAwardRuleModelVO strategyAwardRuleModelVO = strategyRepository.queryStrategyAwardRuleModelVO(strategyId, awardId);
//      2.2 抽奖中过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> raffleCenterEntityRuleActionEntity = this.doCheckRaffleCenterLogic(
                RaffleFactorEntity
                        .builder()
                        .strategyId(strategyId)
                        .userId(userId)
                        .awardId(awardId).build()
                , strategyAwardRuleModelVO.raffleCenterRuleModelList());
        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleCenterEntityRuleActionEntity.getCode())){
            log.info("【临时日志】中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build();

        }


        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();

    }


    /**
     *  @author:wenzhuo4657
        des: 进行抽奖前过滤并返回结果。
    */
    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}
