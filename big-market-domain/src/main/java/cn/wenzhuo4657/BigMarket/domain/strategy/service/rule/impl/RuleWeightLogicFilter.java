package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.annotation.LogicStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.ILogicFilter;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory.DefaultLogicFactory;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @className: RuleWeightLogicFilter
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description: 权重校验。
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {
    private IStrategyRepository repository;

  //  wenzhuo TODO 2024/9/28 :  该变量表示用户消耗积分，由于没有实现查询，暂时写死在此处，
    public  Long userScore=4500L;

    public RuleWeightLogicFilter(IStrategyRepository repository) {
        this.repository = repository;
    }

    /**
     *  @author:wenzhuo4657
        des:
    1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     4000、5000、6000表示用户消耗的积分数量，据此来判断应该使用哪一个权重集合，也就是奖品id集合，
    所谓的权重规则，表示的是根据用户消耗积分的不同来判定其奖品池中的奖品类型的规则。
    */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-权重范围 userId:{} strategyId:{} ruleModel:{}",ruleMatterEntity.getUserId(),ruleMatterEntity.getStrategyId(),ruleMatterEntity.getRuleModel());
        String userId=ruleMatterEntity.getUserId();
        Long stategyId=ruleMatterEntity.getStrategyId();
        String ruleValue = repository.queryStrategyRuleValue(stategyId, ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());

        Map<Long,String> group=getAnalyticalValue(ruleValue);

        if (Objects.isNull(group)||group.isEmpty()){
            return  RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }



        ArrayList<Long> longs = new ArrayList<>(group.keySet());
        Collections.sort(longs);
        Long aLong = longs.stream()
                .filter(key -> userScore >= key)
                .findFirst().orElse(null);


        if (!Objects.isNull(aLong)){
            return  RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode())
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(stategyId)
                            .ruleWeightValueKey(group.get(aLong))
                            .build()
                    ).build();
        }

        return  RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }

    /**
     * @Author wenzhuo4657
     * @Param ruleValue:规则字符串，数据库中由相应字段，其中由三种分割符，依次解析可以得到权重比值
     * @return
     * 返回权重规则集合。
     **/
    private Map<Long, String> getAnalyticalValue(String ruleValue){
        String [] ruleValueGroups=ruleValue.split(Constants.SPACE);
        Map<Long,String> ruleMap=new HashMap<>();
        for (String ruleValuekey:ruleValueGroups){
            if (StringUtils.isEmpty(ruleValuekey)){
                return  ruleMap;
            }
            String[] parts=ruleValuekey.split(Constants.COLON);
            if (parts.length!=2){
                throw  new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValuekey);
            }
            ruleMap.put(Long.parseLong(parts[0]),ruleValuekey);
        }
        return  ruleMap;
    }
}
