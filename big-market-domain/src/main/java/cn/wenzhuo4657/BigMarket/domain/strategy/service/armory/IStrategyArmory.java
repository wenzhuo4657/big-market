package cn.wenzhuo4657.BigMarket.domain.strategy.service.armory;

/**
 * @className: IStrategyArmory
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:40
 * @Version: 1.0
 * @description: 策略装配接口定义
 */
public interface IStrategyArmory {

      /**
         *  des: 根据策略id装配抽奖策略，
       *  装配分为两步骤，第一先装配策略表，第二装配权重。且注意在代码逻辑中有一个截断式的if判断，它表明如果该策略没有权重规则则不进行权重配置。
        *
        *  return :装配成功 ，true;反之false。
       *  假设在程序运行环境安全的情况下，该方法实际上是数据库层面的操作，将其加载到redis中。
         * */


    boolean assembleLotteryStrategy(Long strategyId);



}