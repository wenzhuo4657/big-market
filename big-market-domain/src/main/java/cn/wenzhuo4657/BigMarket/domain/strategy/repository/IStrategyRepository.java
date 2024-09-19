package cn.wenzhuo4657.BigMarket.domain.strategy.repository;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IStrategyRepository
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:36
 * @Version: 1.0
 * @description: 策略服务仓储接口
 */
public interface IStrategyRepository {
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey);

    int getRateRange(Long strategyId);
}