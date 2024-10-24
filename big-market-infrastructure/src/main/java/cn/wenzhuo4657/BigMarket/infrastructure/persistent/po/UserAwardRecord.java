package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户中奖记录表(UserAwardRecord000)实体类
 *
 * @author makejava
 * @since 2024-10-24 08:08:38
 */
public class UserAwardRecord implements Serializable {
    private static final long serialVersionUID = -33647000165884274L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 抽奖策略ID
     */
    private Long strategyId;
    /**
     * 抽奖订单ID【作为幂等使用】
     */
    private String orderId;
    /**
     * 奖品ID
     */
    private Integer awardId;
    /**
     * 奖品标题（名称）
     */
    private String awardTitle;
    /**
     * 中奖时间
     */
    private Date awardTime;
    /**
     * 奖品状态；create-创建、completed-发奖完成
     */
    private String awardState;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public String getAwardTitle() {
        return awardTitle;
    }

    public void setAwardTitle(String awardTitle) {
        this.awardTitle = awardTitle;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public String getAwardState() {
        return awardState;
    }

    public void setAwardState(String awardState) {
        this.awardState = awardState;
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

