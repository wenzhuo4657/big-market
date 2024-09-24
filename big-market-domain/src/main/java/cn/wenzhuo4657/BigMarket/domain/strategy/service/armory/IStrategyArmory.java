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
         *  des: 根据策略id装配抽奖策略
        *
        *  return :装配成功 ，true;反之false。
       *  假设在程序运行环境安全的情况下，该方法实际上是数据库层面的操作，如果想要保证接口单一原则，那么该方法的目的就应该是
         * */


    boolean assembleLotteryStrategy(Long strategyId);

  /**
     *  des: 执行策略，返回随机奖品的id
     * */
    Integer getRandomAwardId(Long strategyId);

}