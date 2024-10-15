package cn.wenzhuo4657.BigMarket.infrastructure.persistent.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽奖活动次数配置表(RaffleActivityCount)实体类
 *
 * @author makejava
 * @since 2024-10-15 19:17:26
 */
public class RaffleActivityCount implements Serializable {
    private static final long serialVersionUID = 326482262165156929L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 活动次数编号
     */
    private Long activityCountId;
    /**
     * 总次数
     */
    private Integer totalCount;
    /**
     * 日次数
     */
    private Integer dayCount;
    /**
     * 月次数
     */
    private Integer monthCount;
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

    public Long getActivityCountId() {
        return activityCountId;
    }

    public void setActivityCountId(Long activityCountId) {
        this.activityCountId = activityCountId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
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

