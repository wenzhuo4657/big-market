package cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: RuleTreeNodeLineEntity
 * @author: wenzhuo4657
 * @date: 2024/10/4
 * @Version: 1.0
 * @description: 规则连线值对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeNodeLineVo {
    /** 规则树ID */
    private String treeId;
    /** 规则Key节点 From */
    private String ruleNodeFrom;
    /** 规则Key节点 To */
    private String ruleNodeTo;
    /** 限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围] */
    private RuleLimitTypeVO ruleLimitType;
    /** 限定值（到下个节点） */
    private RuleLogicCheckTypeVO ruleLimitValue;
}
