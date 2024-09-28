package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;


import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (StrategyAward)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 20:55:24
 */

public interface StrategyAwardDao {


    List<StrategyAwardEntity> queryStrategyAwardListByStrategyId(@Param("strategyId") Long strategyId);
}

