package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.StrategyAwardDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.IRedisService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @className: StrategyRepository
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:47
 * @Version: 1.0
 * @description:
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    private IRedisService redissonService;

    private StrategyAwardDao strategyAwardDao;

    public StrategyRepository(IRedisService redissonService, StrategyAwardDao strategyAwardDao) {
        this.redissonService = redissonService;
        this.strategyAwardDao = strategyAwardDao;
    }

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {

        return null;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {

    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey) {
        return null;
    }

    @Override
    public int getRateRange(Long strategyId) {
        return 0;
    }
}