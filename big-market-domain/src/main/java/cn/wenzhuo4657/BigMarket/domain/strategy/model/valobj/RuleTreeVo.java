package cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @className: RuleTreeEntity
 * @author: wenzhuo4657
 * @date: 2024/10/4
 * @Version: 1.0
 * @description: 规则树值对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeVo {
    /** 规则树ID */
    private String treeId;
    /** 规则树名称 */
    private String treeName;
    /** 规则树描述 */
    private String treeDesc;
    /** 规则根节点 */
    private String treeRootRuleNode;


    /** 规则节点 */
    private Map<String, RuleTreeNodeVo> treeNodeMap;
}
