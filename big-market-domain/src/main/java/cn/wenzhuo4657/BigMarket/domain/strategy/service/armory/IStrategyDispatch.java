package cn.wenzhuo4657.BigMarket.domain.strategy.service.armory;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * @className: IStrategyDispatch
 * @author: wenzhuo4657
 * @date: 2024/9/24
 * @Version: 1.0
 * @description:   策略抽奖调度接口
 */
public interface IStrategyDispatch {


    /**
     *  @author:wenzhuo4657
        des:执行对应策略，返回随机奖品的id
    */
    Integer getRandomAwardId(Long strategyId);


/**
 *  @author:wenzhuo4657
    des: 权重规则抽奖，
*/
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    /**
     * @Author wenzhuo4657
     * @param  key:redis中的key，表示某一策略的缓存，形如 strategyId + _ + ruleWeightValue；
     * @return 执行该策略的随机结果，即奖品id
     */
    Integer getRandomAwardId(String key);


    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);


}
