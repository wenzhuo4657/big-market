package cn.wenzhuo4657.LuckySphere.domain.strategy.service.armory;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

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
     * @param  key:策略概率表的后缀，实质上对应装配方法storeStrategyAwardSearchRateTable，
     *            该方法提出的本意在于，可能存在一种情况（抽奖策略不知道该调取权重策略抽奖还是默认抽奖，仅仅知道该key表示策略概率表的后缀）
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
