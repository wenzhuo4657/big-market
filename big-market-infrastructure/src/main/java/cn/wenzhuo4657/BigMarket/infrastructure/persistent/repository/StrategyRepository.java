package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.StrategyAwardDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.StrategyDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.StrategyRuleDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.StrategyAward;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.StrategyRule;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.IRedisService;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @className: StrategyRepository
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:47
 * @Version: 1.0
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    private IRedisService redissonService;

    private StrategyAwardDao strategyAwardDao;
    private StrategyDao strategyDao;

    private StrategyRuleDao strategyRuleDao;

    @Autowired
    public StrategyRepository(IRedisService redissonService, StrategyAwardDao strategyAwardDao, StrategyDao strategyDao, StrategyRuleDao strategyRuleDao) {
        this.redissonService = redissonService;
        this.strategyAwardDao = strategyAwardDao;
        this.strategyDao = strategyDao;
        this.strategyRuleDao = strategyRuleDao;
    }


    public StrategyRepository(IRedisService redissonService, StrategyAwardDao strategyAwardDao, StrategyDao strategyDao) {
        this.redissonService = redissonService;
        this.strategyAwardDao = strategyAwardDao;
        this.strategyDao = strategyDao;
    }

    public StrategyRepository(IRedisService redissonService, StrategyAwardDao strategyAwardDao) {
        this.redissonService = redissonService;
        this.strategyAwardDao = strategyAwardDao;
    }

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey= Constants.RedisKey.STRATEGY_AWARD_KEY+strategyId;
        List<StrategyAwardEntity> strategyAwardEntityList=redissonService.getValue(cacheKey);
        if (!Objects.isNull(strategyAwardEntityList)&&!strategyAwardEntityList.isEmpty()) return strategyAwardEntityList;

        strategyAwardEntityList=strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);

        redissonService.setValue(cacheKey,strategyAwardEntityList);
        return strategyAwardEntityList;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {

        redissonService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+key,rateRange);
        RMap<Object, Object> map = redissonService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY +key);
        map.putAll(strategyAwardSearchRateTable);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redissonService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY+key,rateKey);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return redissonService.getValue(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redissonService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+key);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        String  cache=Constants.RedisKey.STRATEGY_KEY+strategyId;
        StrategyEntity strategyEntity = redissonService.getValue(cache);
        if (!Objects.isNull(strategyEntity))return  strategyEntity;
        strategyEntity=strategyDao.getStrateEntityByStrategyId(strategyId);
        redissonService.setValue(cache,strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        return strategyRuleDao.queryStrategyRuleEntity(strategyId,ruleModel);

    }
}