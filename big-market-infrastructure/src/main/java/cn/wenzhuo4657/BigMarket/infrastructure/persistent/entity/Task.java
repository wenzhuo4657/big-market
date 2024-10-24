package cn.wenzhuo4657.BigMarket.infrastructure.persistent.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 任务表，发送MQ(Task)实体类
 *
 * @author makejava
 * @since 2024-10-24 08:08:15
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 844609451630171813L;
    /**
     * 自增ID
     */
    private String id;
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息主体
     */
    private String message;
    /**
     * 任务状态；create-创建、completed-完成、fail-失败
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

