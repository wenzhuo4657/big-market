package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * (RuleTree)实体类
 *
 * @author makejava
 * @since 2024-10-05 15:44:08
 */
public class RuleTree implements Serializable {
    private static final long serialVersionUID = -58021041840563785L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 规则树ID
     */
    private String treeId;
    /**
     * 规则树名称
     */
    private String treeName;
    /**
     * 规则树描述
     */
    private String treeDesc;
    /**
     * 规则树根入口规则
     */
    private String treeNodeRuleKey;
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

    public Integer getTreeId() {
        return Integer.valueOf(treeId);
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getTreeDesc() {
        return treeDesc;
    }

    public void setTreeDesc(String treeDesc) {
        this.treeDesc = treeDesc;
    }

    public String getTreeNodeRuleKey() {
        return treeNodeRuleKey;
    }

    public void setTreeNodeRuleKey(String treeNodeRuleKey) {
        this.treeNodeRuleKey = treeNodeRuleKey;
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

