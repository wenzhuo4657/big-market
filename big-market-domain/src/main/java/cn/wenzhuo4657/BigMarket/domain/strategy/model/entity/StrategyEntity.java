package cn.wenzhuo4657.BigMarket.domain.strategy.model.entity;

import cn.wenzhuo4657.BigMarket.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @className: StrategyEntity
 * @author: wenzhuo4657
 * @date: 2024/9/24
 * @Version: 1.0
 * @description: 策略实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 抽奖规则模型 rule_weight,rule_blacklist */
    private String ruleModels;

    /**
     *  @author:wenzhuo4657
        des: 返回规则数组，且做了判空的处理，
    */
    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    /**
     *  @author:wenzhuo4657
        des: 查找是否有规则rule_weight，如果有则返回“rule_weight”，没有返回null
    */

    public String getRuleWeight() {
        String[] ruleModels = this.ruleModels();
        for (String ruleModel : ruleModels) {
            if ("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }
}
