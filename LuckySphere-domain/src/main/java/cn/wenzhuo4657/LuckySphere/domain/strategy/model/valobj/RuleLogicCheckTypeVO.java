package cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj;


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
    ALLOW("0000", "未命中；该奖品未被当前锁捕获，不用走兜底，继续执行"),
    TAKE_OVER("0001","命中；不能发放指定奖品，且注意对于兜底节点而言结果一定是命中"),
            ;

    private final String code;
    private final String info;


}
