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
         * */
    boolean assembleLotteryStrategy(Long strategyId);

  /**
     *  des: 执行策略，返回随机奖品的id
     * */
    Integer getRandomAwardId(Long strategyId);

}