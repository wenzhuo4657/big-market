package cn.wenzhuo4657.BigMarket.domain.strategy.model.entity;

import cn.wenzhuo4657.BigMarket.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
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



    /**
     *  @author:wenzhuo4657
        des:
     1，rule_weight：该规则的含义是抽奖策略的权重装配。

     方法的含义为：如果有权重装配的规则，则返回抽奖规则的比值。ruleValue有三组分隔符号，逐层解析。
    */
    public Map<String, List<Integer>>  getRuleWeightValues() {

        if (!"rule_weight".equals(ruleModel))return  null;
        String [] ruleValueGroups=ruleValue.split(Constants.SPACE);
        Map<String, List<Integer>> resultMap = new HashMap<>();

        for (String ruleValue :ruleValueGroups){
            if (StringUtils.isEmpty(ruleValue)){
                  //  wenzhuo TODO 2024/9/25 : 即便这里的分割符号为空格，出现空值难道不报错吗?
                return resultMap;
            }
            String[] parts = ruleValue.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValue);
            }
            String[] valueStrings = parts[1].split(Constants.SPLIT);
            List<Integer> values = new ArrayList<>();
            for (String valueString : valueStrings) {
                values.add(Integer.parseInt(valueString));
            }
            // 将键和值放入Map中
              //  wenzhuo TODO 2024/9/25 : 这里似乎有问题。没有使用part[0]的值， ruleValue表示该组的整体没有被分割全长度，作为键似乎过于长了
            resultMap.put(ruleValue, values);
        }

    }

}
