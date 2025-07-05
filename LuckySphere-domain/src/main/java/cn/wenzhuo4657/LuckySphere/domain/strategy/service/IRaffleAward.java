package cn.wenzhuo4657.LuckySphere.domain.strategy.service;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/11
 * @description: 策略奖品接口
 */
public interface IRaffleAward {
    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

    /**
     * 根据活动ID查询抽奖奖品列表配置
     *
     * @param activityId 活动ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardListByActivity(Long activityId);

    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param activityId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardListByActivityId(Long activityId);
}
