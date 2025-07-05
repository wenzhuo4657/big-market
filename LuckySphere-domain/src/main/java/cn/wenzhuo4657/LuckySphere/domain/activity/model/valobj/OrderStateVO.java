package cn.wenzhuo4657.LuckySphere.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 订单状态值对象
 */
@Getter
@AllArgsConstructor
public enum OrderStateVO {
    wait_pay("wait_pay","待支付"),
    completed("completed", "完成"),
    ;

    private final String code;
    private final String desc;

}
