package cn.wenzhuo4657.LuckySphere.tigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/5
 * @description: 用户活动次数请求体
 */
@Data
public class UserActivityAccountRequestDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

}

