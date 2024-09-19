package cn.wenzhuo4657.BigMarket.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: StrategyConditionEntity
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:33
 * @Version: 1.0
 * @description:    策略条件实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyConditionEntity {
    private String userId;
    private Integer strategyId;

}