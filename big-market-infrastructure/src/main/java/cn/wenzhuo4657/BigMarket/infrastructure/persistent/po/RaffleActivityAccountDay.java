package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
import java.util.logging.SimpleFormatter;

/**
 * 抽奖活动账户表-日次数(RaffleActivityAccountDay)实体类
 *
 * @author makejava
 * @since 2024-10-24 08:06:12
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaffleActivityAccountDay implements Serializable {
    private static final long serialVersionUID = -70970835227807304L;

    private final static SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
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
     * 日期（yyyy-mm-dd）
     */
    private String day;
    /**
     * 日次数
     */
    private Integer dayCount;
    /**
     * 日次数-剩余
     */
    private Integer dayCountSurplus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public static String currentDay() {
        return dateFormatDay.format(new Date());
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getDayCountSurplus() {
        return dayCountSurplus;
    }

    public void setDayCountSurplus(Integer dayCountSurplus) {
        this.dayCountSurplus = dayCountSurplus;
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

