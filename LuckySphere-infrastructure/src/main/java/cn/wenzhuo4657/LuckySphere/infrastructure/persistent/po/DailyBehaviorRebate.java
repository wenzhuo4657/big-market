package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * 日常行为返利活动配置(DailyBehaviorRebate)实体类
 *
 * @author makejava
 * @since 2024-11-04 15:14:30
 */
public class DailyBehaviorRebate implements Serializable {
    private static final long serialVersionUID = -59205394022136264L;
    /**
     * 自增ID
     */
    private String id;
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
     * 返利配置
     */
    private String rebateConfig;
    /**
     * 状态（open 开启、close 关闭）
     */
    private String state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

