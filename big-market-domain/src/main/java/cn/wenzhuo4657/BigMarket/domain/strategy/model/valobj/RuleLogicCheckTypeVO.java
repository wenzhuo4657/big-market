package cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @className: RuleLogicCheckTypeVO
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description: 抽奖行为实体的校验类型的值对象
 */
@Getter
@AllArgsConstructor
public enum RuleLogicCheckTypeVO {
    /**
     *  @author:wenzhuo4657
        des:
     这两条菜单规则表示规则过滤是否命中，即是否生效。
    */
    ALLOW("0000", "未命中；执行后续的流程，不受规则引擎影响"),
    TAKE_OVER("0001","命中；后续的流程，受规则引擎执行结果影响"),
            ;

    private final String code;
    private final String info;


}
