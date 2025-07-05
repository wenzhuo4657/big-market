package cn.wenzhuo4657.LuckySphere.domain.credit.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description: 交易名称/交易分类 值对象
 */
@Getter
@AllArgsConstructor
public enum TradeNameVO {

    REBATE("行为返利"),
    CONVERT_SKU("兑换抽奖"),

    ;

    private final String name;

}
