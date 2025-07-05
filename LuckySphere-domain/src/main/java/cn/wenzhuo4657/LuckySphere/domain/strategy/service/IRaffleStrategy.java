package cn.wenzhuo4657.LuckySphere.domain.strategy.service;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.RaffleAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @className: IRaffleStrategy
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description: 抽奖策略过程定义
 */
public interface IRaffleStrategy {

    /**
     *  @author:wenzhuo4657
        des: 执行抽奖并返回结果。
    */

    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}
