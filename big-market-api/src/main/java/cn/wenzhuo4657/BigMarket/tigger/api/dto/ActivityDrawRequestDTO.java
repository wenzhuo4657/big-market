package cn.wenzhuo4657.BigMarket.tigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/29
 * @description: 活动抽奖请求对象
 */
@Data
public class ActivityDrawRequestDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;
}
