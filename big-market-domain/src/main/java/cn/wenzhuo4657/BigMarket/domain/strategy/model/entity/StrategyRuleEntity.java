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
     return : Map<String, List<Integer>>
     k：
     v：表示对应的奖品id列表
    */


    public Map<String, List<Integer>>  getRuleWeightValues() {

        if (!"rule_weight".equals(ruleModel))return  null;
        String [] ruleValueGroups=ruleValue.split(Constants.SPACE);
        Map<String, List<Integer>> resultMap = new HashMap<>();

        for (String ruleValue :ruleValueGroups){
            if (StringUtils.isEmpty(ruleValue)){
                /**
                 *  @author:wenzhuo4657
                    des:
                 单空格分割字符串，连续空格会出现”“，直接返回的含义暂且理解为配置错误或者传输过程中的前后多余的空格。
                */
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
            /**
             *  @author:wenzhuo4657
                des:
             ruleValue是类似100001_6000:102,103,104,105,106,107,108,109的值
             需要注意其分隔符号’:‘，该符号可以作为redis中键的分隔符号，这意味着如果想要在redis中加载权值的mqp结构会极为方便。
             而且此处我选择的kryo序列化方式，加上该格式，会使时间效率进一步提升。
            */
            resultMap.put(ruleValue, values);
        }
        return  resultMap;

    }

}
