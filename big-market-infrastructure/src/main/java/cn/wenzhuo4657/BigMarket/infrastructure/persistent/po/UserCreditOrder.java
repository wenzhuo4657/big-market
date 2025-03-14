package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户积分订单记录(UserCreditOrder000)实体类
 *
 * @author makejava
 * @since 2024-11-09 14:14:11
 */
public class UserCreditOrder implements Serializable {
    private static final long serialVersionUID = 720446723529660345L;
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
     * 交易名称
     */
    private String tradeName;
    /**
     * 交易类型；forward-正向、reverse-逆向
     */
    private String tradeType;
    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;
    /**
     * 业务仿重ID - 外部透传。返利、行为等唯一标识
     */
    private String outBusinessNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }


    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getOutBusinessNo() {
        return outBusinessNo;
    }

    public void setOutBusinessNo(String outBusinessNo) {
        this.outBusinessNo = outBusinessNo;
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

