package cn.wenzhuo4657.BigMarket.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @className: StrategyRuleEntity
 * @author: wenzhuo4657
 * @date: 2024/9/24
 * @Version: 1.0
 * @description: 策略规则实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRuleEntity {

    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID【规则类型为策略，则不需要奖品ID】 */
    private Integer awardId;
    /** 抽象规则类型；1-策略规则、2-奖品规则 */
    private Integer ruleType;
    /** 抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】 */
    private String ruleModel;
    /** 抽奖规则比值 */
    private String ruleValue;
    /** 抽奖规则描述 */
    private String ruleDesc;

    public Map<String, List<Integer>>  getRuleWeightValues(){
          //  wenzhuo TODO 2024/9/24 : 不是很理解这里的截断，该规则的代表的含义是什么？既然该实体分为策略规则和奖品规则，为什么不首先区分这个？该规则的含义是奖品和规则通用的吗？
        if (!"rule_weight".equals(ruleModel))return  null;
        String [] ruleValueGroups=
    }

}
