package cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description: 行为类型值对象
 */
@Getter
@AllArgsConstructor
public enum BehaviorTypeVO {

    SIGN("sign", "签到（日历）"),
    OPENAI_PAY("openai_pay", "openai 外部支付完成"),
    ;

    private final String code;
    private final String info;

}
