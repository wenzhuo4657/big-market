package cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description: 任务状态值对象
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {
    create("create", "创建"),
    complete("complete", "发送完成"),
    fail("fail", "发送失败"),
    ;

    private final String code;
    private final String desc;
}
