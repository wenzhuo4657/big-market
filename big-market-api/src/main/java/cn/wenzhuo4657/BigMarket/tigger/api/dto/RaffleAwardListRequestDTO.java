package cn.wenzhuo4657.BigMarket.tigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/10
 * @description: queryRaffleAwardList请求dto
 */
@Data
public class RaffleAwardListRequestDTO implements Serializable {
    // 用户ID
    private String userId;
    // 抽奖活动ID
    private Long activityId;
}
