package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.AbstractLogicChain;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @className: RuleWeightLogicFilter
 * @author: wenzhuo4657
 * @date: 14:56
 * @Version: 2.0
 * @description: 权重责任链
 */
@Slf4j
@Component("rule_weight")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RuleWeightLogicChain extends AbstractLogicChain {
    private IStrategyRepository repository;


    public Long userScore = 0L;


    protected IStrategyDispatch strategyDispatch;

    @Resource
    private IStrategyRepository strategyRepository;

    public RuleWeightLogicChain(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
    }

    /**
     * @author:wenzhuo4657 des:
     * 1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 4000、5000、6000表示用户消耗的积分数量，据此来判断应该使用哪一个权重集合，也就是奖品id集合，
     * 所谓的权重规则，表示的是根据用户消耗积分的不同来判定其奖品池中的奖品类型的规则。
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链-权重开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        Map<Long, String> group = getAnalyticalValue(ruleValue);
        if (null == group || group.isEmpty()) {
            return next().logic(userId, strategyId);  //截断处理，表示没有权重规则配置
        }

        ArrayList<Long> keys = new ArrayList<>(group.keySet());
        Collections.sort(keys);

        userScore = repository.queryUserDepleteAmonunt(userId);
        Long nextValue = keys.stream().sorted(Comparator.reverseOrder()).filter(analyticalSortedKeyValue -> userScore >= analyticalSortedKeyValue).findFirst().orElse(null);//找到适用的权重规则
        if (null != nextValue) {
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, group.get(nextValue));
            boolean res = subAwardStock(userId, strategyId, awardId);
            if (res) {
                log.info("抽奖责任链-权重接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder().awardId(awardId).logicModel(DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode()).build();
            }
        }
        log.info("抽奖责任链-权重放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    /**
     * @return 返回权重规则集合。
     * @Author wenzhuo4657
     * @Param ruleValue:规则字符串，数据库中由相应字段，其中由三种分割符，依次解析可以得到权重比值
     **/
    private Map<Long, String> getAnalyticalValue(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleMap = new HashMap<>();
        for (String ruleValuekey : ruleValueGroups) {
            if (StringUtils.isEmpty(ruleValuekey)) {
                return ruleMap;
            }
            String[] parts = ruleValuekey.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValuekey);
            }
            ruleMap.put(Long.parseLong(parts[0]), ruleValuekey);
        }
        return ruleMap;
    }

    /**
     * @author:wenzhuo4657 des:
     * 扣减奖品库存
     */
    private boolean subAwardStock(String userId, Long strategyId, Integer awardId) {

        log.info("权重责任链-库存扣减 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        Boolean aBoolean = strategyDispatch.subtractionAwardStock(strategyId, awardId);
        if (aBoolean) {
            log.info("规则过滤-库存扣减-成功 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            strategyRepository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder().awardId(awardId).strategyId(strategyId).build());
            return true;
        } else {
            return false;
        }

    }
}
