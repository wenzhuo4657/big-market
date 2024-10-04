package cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj;


import cn.wenzhuo4657.BigMarket.types.common.Constants;
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

    public String[] raffleCenterRuleModelList(){
        List<String> ruleModelList = new ArrayList<>();
        String[] split = ruleModels.split(Constants.SPLIT);
        for (String ruleModelValue:split){
            if (DefaultLogicFactory.LogicModel.isCenter(ruleModelValue)){
                ruleModelList.add(ruleModelValue);
            }
        }
        return ruleModelList.toArray(new String[0]);
    }

    public String[] raffleAfterRuleModelList(){
        List<String> ruleModelList = new ArrayList<>();
        String[] split = ruleModels.split(Constants.SPLIT);
        for (String ruleModelValue:split){
            if (DefaultLogicFactory.LogicModel.isAfter(ruleModelValue)){
                ruleModelList.add(ruleModelValue);
            }
        }
        return ruleModelList.toArray(new String[0]);
    }
}
