package cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: RaffleAwardEntity
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description: 抽奖奖品实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardEntity {
    /** 奖品ID */
    private Integer awardId;
    /** 抽奖奖品标题 */
    private String awardTitle;
    /** 奖品配置信息 */
    private String awardConfig;
    /** 奖品顺序号 */
    private Integer sort;


}
