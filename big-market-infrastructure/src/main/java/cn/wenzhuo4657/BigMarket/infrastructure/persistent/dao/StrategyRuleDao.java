package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;


import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (StrategyRule)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 20:55:40
 */
public interface StrategyRuleDao {


    StrategyRuleEntity queryStrategyRuleEntity(@Param("strategyId") Long strategyId, @Param("ruleModel") String ruleModel);

      //  wenzhuo TODO 2024/11/3 : 该sql执行进行了分表？不知道为何总是很有规律的，一次db00数据源，一次db01数据源
    String queryStrategyRuleValue(StrategyRule strategyRule);
}

