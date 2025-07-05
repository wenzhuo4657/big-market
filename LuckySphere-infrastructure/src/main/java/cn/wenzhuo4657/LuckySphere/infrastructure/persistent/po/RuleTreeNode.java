package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * (RuleTreeNode)实体类
 *
 * @author makejava
 * @since 2024-10-05 15:44:36
 */
public class RuleTreeNode implements Serializable {
    private static final long serialVersionUID = -78921518600692250L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 规则树ID
     */
    private String treeId;
    /**
     * 规则Key
     */
    private String ruleKey;
    /**
     * 规则描述
     */
    private String ruleDesc;
    /**
     * 规则比值
     */
    private String ruleValue;
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

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
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

