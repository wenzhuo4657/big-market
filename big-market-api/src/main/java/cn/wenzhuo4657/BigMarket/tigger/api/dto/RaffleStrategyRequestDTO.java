package cn.wenzhuo4657.BigMarket.tigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/10
 * @description:
 */
@Data
public class RaffleStrategyRequestDTO  implements Serializable {
    // 抽奖策略ID
    private Long strategyId;
}
