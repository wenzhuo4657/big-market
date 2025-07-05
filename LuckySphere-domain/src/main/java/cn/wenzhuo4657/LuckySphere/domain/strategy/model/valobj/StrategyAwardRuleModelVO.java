package cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj;


import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: StrategyAwardRuleModelVO
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description:
 * 抽奖策略规则规则值对象
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {
    private String ruleModels;

}
