package cn.wenzhuo4657.BigMarket.domain.strategy.repository;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyRuleEntity;

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

      /**
         *  des:
       *  如果redis中存在不为空的键值对，直接返回，否则从mysql中查询返回，并更新redis中的键值对。
         * */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

      /**
         *  des:
       *  在redis中存储/更新对应策略的抽奖策略范围值[0，rateRange）和<策略范围值-奖品id >分布表
         * */
    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);


      /**
         *  des:
       *  返回对应奖品id
         * */
    Integer getStrategyAwardAssemble(String key, Integer rateKey);

      /**
         *  des:
       *  查询对应策略的分布范围
         * */
        //  wenzhuo TODO 2024/9/26 : 其实现似乎写错了，并没有看到直接测策略id的装配
    int getRateRange(Long strategyId);


    int getRateRange(String key);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);



}