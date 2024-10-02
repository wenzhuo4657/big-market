package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain;

/**
 * @className: ILogicChain
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description: 逻辑责任链行为定义接口
 */
public interface ILogicChain {

    /**
     * 责任链接口
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    Integer logic(String userId, Long strategyId);
}
