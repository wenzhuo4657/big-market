package cn.wenzhuo4657.BigMarket.domain.strategy.service.raffle;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.AbstractRaffleStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.filter.ILogicFilter;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @className: DefaultRaffleStrategy
 * @author: wenzhuo4657
 * @date: 2024/9/28
 * @Version: 1.0
 * @description:
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {


    public DefaultRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(strategyRepository, strategyDispatch, defaultChainFactory, defaultTreeFactory);

    }

\

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        if (logics == null || 0 == logics.length)
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder().code(RuleLogicCheckTypeVO.ALLOW.getCode()).info(RuleLogicCheckTypeVO.ALLOW.getInfo()).build();

        Map<String, ILogicFilter<RuleActionEntity.RaffleCenterEntity>> logicFilterMap = defaultLogicFactory.openLogicFilter();

        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionEntity = null;
        for (String ruleModel : logics) {
            ILogicFilter<RuleActionEntity.RaffleCenterEntity> logicFilter = logicFilterMap.get(ruleModel);
            if (Objects.isNull(logicFilter)) {
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
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
