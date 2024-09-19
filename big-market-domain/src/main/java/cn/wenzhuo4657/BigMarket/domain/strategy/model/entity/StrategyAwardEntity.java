package cn.wenzhuo4657.BigMarket.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @className: StrategyAwardEnity
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:31
 * @Version: 1.0
 * @description:  策略奖品实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyAwardEntity {
    private Long strategyId;
    private Integer awardId;
    private Integer awardCount;
    private Integer awardCountSurplus;
    private BigDecimal awardRate;
}