package cn.wenzhuo4657.BigMarket.domain.strategy.service.raffle;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.ILogicFilter;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
            RuleMatterEntity ruleMatterEntity=new RuleMatterEntity(raffleFactorEntity.getUserId(),raffleFactorEntity.getStrategyId(),null,DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());
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
            RuleMatterEntity ruleMatterEntity=new RuleMatterEntity(raffleFactorEntity.getUserId(),raffleFactorEntity.getStrategyId(),null,DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());
            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())){
                return ruleActionEntity;
            }

        }

        return ruleActionEntity;
    }
}
