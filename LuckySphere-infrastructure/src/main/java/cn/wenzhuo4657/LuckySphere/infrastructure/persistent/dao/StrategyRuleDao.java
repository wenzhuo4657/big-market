package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;


import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Param;

/**
 * (StrategyRule)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 20:55:40
 */
public interface StrategyRuleDao {


    StrategyRuleEntity queryStrategyRuleEntity(@Param("strategyId") Long strategyId, @Param("ruleModel") String ruleModel);


    String queryStrategyRuleValue(StrategyRule strategyRule);
}

