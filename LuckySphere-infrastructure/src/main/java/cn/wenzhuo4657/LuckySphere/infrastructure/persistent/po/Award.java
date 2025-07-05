package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;

import java.util.Date;
import java.io.Serializable;

/**
 * (Award)实体类
 *
 * @author makejava
 * @since 2024-09-18 20:54:36
 */
public class Award implements Serializable {
    private static final long serialVersionUID = -67986633979772380L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 抽奖奖品ID - 内部流转使用
     */
    private Integer awardId;
    /**
     * 奖品对接标识 - 每一个都是一个对应的发奖策略
     */
    private String awardKey;
    /**
     * 奖品配置信息
     */
    private String awardConfig;
    /**
     * 奖品内容描述
     */
    private String awardDesc;
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

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public String getAwardKey() {
        return awardKey;
    }

    public void setAwardKey(String awardKey) {
        this.awardKey = awardKey;
    }

    public String getAwardConfig() {
        return awardConfig;
    }

    public void setAwardConfig(String awardConfig) {
        this.awardConfig = awardConfig;
    }

    public String getAwardDesc() {
        return awardDesc;
    }

    public void setAwardDesc(String awardDesc) {
        this.awardDesc = awardDesc;
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

