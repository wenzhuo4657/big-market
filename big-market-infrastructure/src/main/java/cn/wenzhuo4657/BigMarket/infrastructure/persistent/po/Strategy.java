package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * (Strategy)实体类
 *
 * @author makejava
 * @since 2024-09-25 19:36:40
 */
public class Strategy implements Serializable {
    private static final long serialVersionUID = 943604272940297770L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 抽奖策略ID
     */
    private Long strategyId;
    /**
     * 抽奖策略描述
     */
    private String strategyDesc;
    /**
     * 规则模型，rule配置的模型同步到此表，便于使用
     */
    private String ruleModels;
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

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyDesc() {
        return strategyDesc;
    }

    public void setStrategyDesc(String strategyDesc) {
        this.strategyDesc = strategyDesc;
    }

    public String getRuleModels() {
        return ruleModels;
    }

    public void setRuleModels(String ruleModels) {
        this.ruleModels = ruleModels;
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

