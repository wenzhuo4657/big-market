package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @className: ILogicFilter
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description: 逻辑规则顶级父类接口定义
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {
    /**
     *  @author:wenzhuo4657
        des:
    */
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}
