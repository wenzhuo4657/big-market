package cn.wenzhuo4657.BigMarket.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: RuleMatterEntity
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description:
 */
  //  wenzhuo TODO 2024/9/26 :该实体的含义不是很明白，其中某两个字段和RaffleFactorEntity 是重合的，那么难道不可以根据上述两个字段查询后两个字段吗？将其设置为list难道不是更合理吗？

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleMatterEntity {
    /** 用户ID */
    private String userId;
    /** 策略ID */
    private Long strategyId;
    /** 抽奖奖品ID【规则类型为策略，则不需要奖品ID】 */
    private Integer awardId;
    /** 抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】 */
    private String ruleModel;
}
