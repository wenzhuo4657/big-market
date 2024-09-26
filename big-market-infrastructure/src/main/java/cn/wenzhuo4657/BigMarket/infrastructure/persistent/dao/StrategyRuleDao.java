package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;


import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyRuleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (StrategyRule)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 20:55:40
 */
@Mapper
public interface StrategyRuleDao {


    StrategyRuleEntity queryStrategyRuleEntity(@Param("strategyId") Long strategyId, @Param("ruleModel") String ruleModel);

    String queryStrategyRuleValue(
            @Param("strategyId") Long strategyId,
            @Param("awardId") Integer awardId,
            @Param("ruleModel") String ruleModel);
}

