package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

/**
 * 抽奖活动账户表-月次数(RaffleActivityAccountMonth)实体类
 *
 * @author makejava
 * @since 2024-10-24 08:07:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleActivityAccountMonth implements Serializable {
    private static final long serialVersionUID = -91227961735669064L;
    private final static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");
    /**
     * 自增ID
     */
    private long id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 月（yyyy-mm）
     */
    private String month;
    /**
     * 月次数
     */
    private Integer monthCount;
    /**
     * 月次数-剩余
     */
    private Integer monthCountSurplus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public static String currentMonth() {
        return dateFormatMonth.format(new Date());
    }


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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

    public Integer getMonthCountSurplus() {
        return monthCountSurplus;
    }

    public void setMonthCountSurplus(Integer monthCountSurplus) {
        this.monthCountSurplus = monthCountSurplus;
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

