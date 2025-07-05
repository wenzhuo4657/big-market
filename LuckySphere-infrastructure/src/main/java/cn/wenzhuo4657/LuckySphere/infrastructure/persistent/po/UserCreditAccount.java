package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户积分账户(UserCreditAccount)实体类
 *
 * @author makejava
 * @since 2024-11-09 09:22:12
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreditAccount implements Serializable,Comparable<UserCreditAccount> {
    private static final long serialVersionUID = -10410681505434122L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NotNull UserCreditAccount o) {
        return this.id.compareTo(o.id);
    }

    /**
     * 自增ID
     */
    private Long  id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 总积分，显示总账户值，记得一个人获得的总积分
     */
    private BigDecimal totalAmount;
    /**
     * 可用积分，每次扣减的值
     */
    private BigDecimal availableAmount;
    /**
     * 账户状态【open - 可用，close - 冻结】
     */
    private String accountStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
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

