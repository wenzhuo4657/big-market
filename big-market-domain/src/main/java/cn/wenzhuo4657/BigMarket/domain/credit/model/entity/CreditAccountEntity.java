package cn.wenzhuo4657.BigMarket.domain.credit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description: 积分账户实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAccountEntity {

    /** 用户ID */
    private String userId;
    /** 可用积分， */
    private BigDecimal adjustAmount;

}

