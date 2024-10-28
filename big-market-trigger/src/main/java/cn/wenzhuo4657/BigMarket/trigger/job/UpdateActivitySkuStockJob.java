package cn.wenzhuo4657.BigMarket.trigger.job;

import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivitySkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component()
public class UpdateActivitySkuStockJob {

    @Resource
    private IRaffleActivitySkuStockService skuStock;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            ActivitySkuStockKeyVO activitySkuStockKeyVO = skuStock.takeQueueValue();
            if (null == activitySkuStockKeyVO) return;
            log.info("定时任务，更新活动sku库存 sku:{} activityId:{}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
            skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
        } catch (Exception e) {
            log.error("定时任务，更新活动sku库存失败", e);
        }
    }

}