package cn.wenzhuo4657.BigMarket.domain.strategy.service.raffle;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.ILogicFilter;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory.DefaultLogicFactory;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: DefaultRaffleStrategy
 * @author: wenzhuo4657
 * @date: 2024/9/28
 * @Version: 1.0
 * @description:
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy{
    DefaultLogicFactory defaultLogicFactory;

    @Autowired
    public DefaultRaffleStrategy( IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch, DefaultLogicFactory defaultLogicFactory) {
        super(strategyRepository, strategyDispatch);
        this.defaultLogicFactory = defaultLogicFactory;
    }

    /**
     *  @author:wenzhuo4657
        des:
    public <T extends RuleActionEntity.RaffleEntity>    Map<String, ILogicFilter<T>> openLogicFilter() {
    return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }

     虽然这里的泛型约束失效了，但并不意味着可以随意取出集合中的元素，该泛型转换在抽奖策略中表示为只能取出某一类型的策略，否则会报错，
     实际上，尽管集合内部的元素不符合泛型管控，但是取出的元素依旧在泛型检查中，即(Map<String, ILogicFilter<T>>) 。


    */
    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        Map<String, ILogicFilter<RuleActionEntity.RaffleBeforeEntity>> LogicFilterGroup
                = defaultLogicFactory.openLogicFilter();
//        1，如果存在黑名单规则，则优先进行黑名单过滤
        String rule_blacklist = Arrays.stream(logics)
                .filter(str -> str.contains(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode()))
                .findFirst()
                .orElse(null);
        if (StringUtils.isNotBlank(rule_blacklist)){
            ILogicFilter<RuleActionEntity.RaffleBeforeEntity> raffleEntityILogicFilter = LogicFilterGroup.get(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());
            RuleMatterEntity ruleMatterEntity=new RuleMatterEntity(raffleFactorEntity.getUserId(),raffleFactorEntity.getStrategyId(), raffleFactorEntity.getAwardId(), DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());
            RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> result = raffleEntityILogicFilter.filter(ruleMatterEntity);
            if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(result.getCode())){
                return result;
            }
        }
//        2，其他规则过滤
        List<String> ruleList = Arrays.stream(logics)
                .filter(s -> !s.equals(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode()))
                .collect(Collectors.toList());
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = null;
        for (String ruleModel:ruleList){
            ILogicFilter<RuleActionEntity.RaffleBeforeEntity> logicFilter = LogicFilterGroup.get(ruleModel);
            RuleMatterEntity ruleMatterEntity=
                    new RuleMatterEntity
                            (raffleFactorEntity.getUserId(),raffleFactorEntity.getStrategyId(), raffleFactorEntity.getAwardId(), ruleModel);
            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())){
                return ruleActionEntity;
            }

        }

        return ruleActionEntity;
    }

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        if (logics==null ||0==logics.length)
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();

        Map<String, ILogicFilter<RuleActionEntity.RaffleCenterEntity>> logicFilterMap = defaultLogicFactory.openLogicFilter();

        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionEntity=null;
        for (String ruleModel : logics) {
            ILogicFilter<RuleActionEntity.RaffleCenterEntity> logicFilter = logicFilterMap.get(ruleModel);
            if (Objects.isNull(logicFilter)){
                throw  new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(raffleFactorEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(ruleModel);

            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            log.info("抽奖中规则过滤 userId: {} ruleModel: {} code: {} info: {}", raffleFactorEntity.getUserId(), ruleModel, ruleActionEntity.getCode(), ruleActionEntity.getInfo());
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) return ruleActionEntity;
        }


        return ruleActionEntity;
    }
}
