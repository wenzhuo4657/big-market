package cn.wenzhuo4657.BigMarket.domain.strategy.service.armory;

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

    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

}
