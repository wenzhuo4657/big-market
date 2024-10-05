package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * (RuleTreeNodeLine)实体类
 *
 * @author makejava
 * @since 2024-10-05 15:44:49
 */
public class RuleTreeNodeLine implements Serializable {
    private static final long serialVersionUID = 308768730381842739L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 规则树ID
     */
    private String treeId;
    /**
     * 规则Key节点 From
     */
    private String ruleNodeFrom;
    /**
     * 规则Key节点 To
     */
    private String ruleNodeTo;
    /**
     * 限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围];
     */
    private String ruleLimitType;
    /**
     * 限定值（到下个节点）
     */
    private String ruleLimitValue;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreeId() {
        return treeId;
    }


    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getRuleNodeFrom() {
        return ruleNodeFrom;
    }

    public void setRuleNodeFrom(String ruleNodeFrom) {
        this.ruleNodeFrom = ruleNodeFrom;
    }

    public String getRuleNodeTo() {
        return ruleNodeTo;
    }

    public void setRuleNodeTo(String ruleNodeTo) {
        this.ruleNodeTo = ruleNodeTo;
    }

    public String getRuleLimitType() {
        return ruleLimitType;
    }

    public void setRuleLimitType(String ruleLimitType) {
        this.ruleLimitType = ruleLimitType;
    }

    public String getRuleLimitValue() {
        return ruleLimitValue;
    }

    public void setRuleLimitValue(String ruleLimitValue) {
        this.ruleLimitValue = ruleLimitValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

