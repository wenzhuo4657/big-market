package cn.wenzhuo4657.BigMarket.trigger.job;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/8
 * @description:更新奖品库存任务
 */
@Slf4j
@Component
public class UpdateAwardStockJob {
    @Resource
    private IRaffleStock raffleStock;
    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
            if (null == strategyAwardStockKeyVO) return;
            log.info("定时任务，更新奖品消耗库存 strategyId:{} awardId:{}", strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
            raffleStock.updateStrategyAwardStock(strategyAwardStockKeyVO.getStrategyId(),strategyAwardStockKeyVO.getAwardId());
        } catch (InterruptedException e) {
            log.error("定时任务，更新奖品消耗库存失败", e);
        }
    }
}
