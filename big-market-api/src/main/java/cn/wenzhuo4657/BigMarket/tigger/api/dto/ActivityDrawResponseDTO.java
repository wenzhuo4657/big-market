package cn.wenzhuo4657.BigMarket.tigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/29
 * @description: 活动抽奖响应对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDrawResponseDTO implements Serializable {
    // 奖品ID
    private Integer awardId;
    // 奖品标题
    private String awardTitle;
    // 排序编号【策略奖品配置的奖品顺序编号】
    private Integer awardIndex;
}
