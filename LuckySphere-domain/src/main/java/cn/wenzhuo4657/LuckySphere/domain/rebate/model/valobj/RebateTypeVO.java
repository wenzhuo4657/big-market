package cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/4
 * @description: 返利类型
 */
@Getter
@AllArgsConstructor
public enum RebateTypeVO {
    SKU("sku", "活动库存充值商品"),
    INTEGRAL("integral", "用户活动积分"),
    ;

    private final String code;
    private final String info;
}
