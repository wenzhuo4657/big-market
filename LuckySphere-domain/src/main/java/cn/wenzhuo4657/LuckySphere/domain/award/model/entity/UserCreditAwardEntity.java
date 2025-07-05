package cn.wenzhuo4657.LuckySphere.domain.award.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/7
 * @description: 用户积分奖品实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditAwardEntity {

    /** 用户ID */
    private String userId;
    /** 积分值 */
    private BigDecimal creditAmount;

}
