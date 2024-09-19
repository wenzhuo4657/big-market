package cn.wenzhuo4657.BigMarket.domain.strategy.service.armory;

import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import org.springframework.stereotype.Service;

/**
 * @className: StrategyArmory
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:45
 * @Version: 1.0
 * @description:
 */
@Service
public class StrategyArmory implements  IStrategyArmory{

    private IStrategyRepository strategyRepository;

    public StrategyArmory(IStrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {

        return false;
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        return null;
    }
}