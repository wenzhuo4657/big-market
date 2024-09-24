package cn.wenzhuo4657.BigMarket.domain.strategy.service.armory;

/**
 * @className: StrategyArmoryDispatch
 * @author: wenzhuo4657
 * @date: 2024/9/24
 * @Version: 1.0
 * @description:
 */
public class StrategyArmoryDispatch implements IStrategyDispatch,IStrategyArmory{
    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        return false;
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        return null;
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        return null;
    }
}
