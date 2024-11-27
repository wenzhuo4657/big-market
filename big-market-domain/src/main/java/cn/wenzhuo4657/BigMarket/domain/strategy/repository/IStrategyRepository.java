package cn.wenzhuo4657.BigMarket.domain.strategy.repository;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleTreeVo;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleWeightVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

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

    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);


    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    /**
     *  @author:wenzhuo4657
        des: 查询规则树
     1，从redis中查询，如果不存在去mysql中查询，
     2，在mysql中查询到对应数据后，将其装配到符合规则树的定义。
    */
    RuleTreeVo queryRuleTreeVOByTreeId(String treeId);


    /**
     * 缓存奖品库存
     *
     * @param cacheKey   key
     * @param awardCount 库存值
     * 注意：仅仅当redis中存在cacheKey键时才进行缓存，实际上该方法表示该数据在redis中的初始化，而维护该数据由其他操作完成。
     */
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    /**
     * 缓存key，decr 方式扣减库存
     *
     * @param cacheKey 缓存Key
     * @return 扣减结果
     * 注意：在此处并没有更新到mysql数据库中，也没有在redis中更新cacheKey的键值对，
     * 而是加锁扣减，生成了一个新的键值对，lockKey=cacheKey+Constants.UNDERLINE+surplus：“lock”;
     * 根据该键值对设置的成功与否来表示扣减是否成功，该操作的好处目前看来有
     * 1，使redis中的库存键值对无锁，取而代之的是表示扣减库存的锁，
     */
    Boolean subtractionAwardStock(String cacheKey);

    /**
     * redis中写入奖品库存消费队列
     *
     * @param strategyAwardStockKeyVO 对象值对象
     * 注意：延迟队列写入，时间间隔为3分钟
     */
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);

    /**
     * 从redis中获取奖品库存消费队列头
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * mysql中自减奖品库存消耗，
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);


    /**
     * 根据策略ID+奖品ID的唯一值组合，查询奖品信息
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 奖品信息
     */
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);

    Long queryStrategyIdByActivityId(Long activityId);

    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);

    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);

    Long queryUserDepleteAmonunt(String userId);
}