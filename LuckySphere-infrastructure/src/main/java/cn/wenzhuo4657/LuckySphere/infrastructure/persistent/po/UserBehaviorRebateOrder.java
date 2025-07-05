package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户行为返利流水订单表(UserBehaviorRebateOrder000)实体类
 *
 * @author makejava
 * @since 2024-11-04 15:13:32
 */
public class UserBehaviorRebateOrder implements Serializable {
    private static final long serialVersionUID = 570943284147312907L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 自增ID
     */
    private long id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 行为类型（sign 签到、openai_pay 支付）
     */
    private String behaviorType;
    /**
     * 返利描述
     */
    private String rebateDesc;
    /**
     * 返利类型（sku 活动库存充值商品、integral 用户活动积分）
     */
    private String rebateType;
    /**
     * 返利配置【sku值，积分值】
     */
    private String rebateConfig;
    /**
     * 业务ID - 拼接的唯一值
     */
    private String bizId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /** 业务仿重ID - 外部透传，方便查询使用 */
    private String outBusinessNo;

    public String getOutBusinessNo() {
        return outBusinessNo;
    }

    public void setOutBusinessNo(String outBusinessNo) {
        this.outBusinessNo = outBusinessNo;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getRebateDesc() {
        return rebateDesc;
    }

    public void setRebateDesc(String rebateDesc) {
        this.rebateDesc = rebateDesc;
    }

    public String getRebateType() {
        return rebateType;
    }

    public void setRebateType(String rebateType) {
        this.rebateType = rebateType;
    }

    public String getRebateConfig() {
        return rebateConfig;
    }

    public void setRebateConfig(String rebateConfig) {
        this.rebateConfig = rebateConfig;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
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

