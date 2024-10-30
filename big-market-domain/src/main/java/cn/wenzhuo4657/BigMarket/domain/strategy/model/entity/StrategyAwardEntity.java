package cn.wenzhuo4657.BigMarket.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @className: StrategyAwardEntity
 * @author: wenzhuo4657
 * @date:  19:27
 * @Version: 2.0
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyAwardEntity {
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;
    /** 抽奖奖品标题 */
    private String awardTitle;
    /** 抽奖奖品副标题 */
    private String awardSubtitle;
    /** 奖品库存总量 */
    private Integer awardCount;
    /** 奖品库存剩余 */
    private Integer awardCountSurplus;
    /** 奖品中奖概率 */
    private BigDecimal awardRate;
    /** 排序 */
    private Integer sort;
    /** 规则模型，rule配置的模型同步到此表，便于使用 */
    private String ruleModels;
}