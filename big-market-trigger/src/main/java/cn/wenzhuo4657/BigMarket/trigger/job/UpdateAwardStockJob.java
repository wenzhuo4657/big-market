package cn.wenzhuo4657.BigMarket.trigger.job;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleStock;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedissonClient redissonClient;
    @XxlJob("updateAwardStockJob")
    public void exec() {

        RLock lock = redissonClient.getLock("big-market-updateAwardStockJob");
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(3, 0, TimeUnit.SECONDS);
            if (!isLocked) return;
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
            if (null == strategyAwardStockKeyVO) return;
            log.info("定时任务，更新奖品消耗库存 strategyId:{} awardId:{}", strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
            raffleStock.updateStrategyAwardStock(strategyAwardStockKeyVO.getStrategyId(),strategyAwardStockKeyVO.getAwardId());
        } catch (InterruptedException e) {
            log.error("定时任务，更新奖品消耗库存失败", e);
        }finally {

            if (isLocked){
                lock.unlock();
            }

        }
    }
}
