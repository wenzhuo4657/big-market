package cn.wenzhuo4657.BigMarket.domain.activity.model.entity;

import lombok.Data;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/24
 * @description: 用户参与抽奖活动实体对象
 */
@Data
public class PartakeRaffleActivityEntity {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;
}