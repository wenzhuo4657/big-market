package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;


import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Strategy)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 20:51:37
 */

public interface StrategyDao {


    StrategyEntity getStrateEntityByStrategyId(Long strategyId);
}

