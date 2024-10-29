package cn.wenzhuo4657.BigMarket.domain.activity.service.armory;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivitySkuEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/21
 * @description: 活动sku预热
 *
 */
@Slf4j
@Service
public class ActivityArmory implements IActivityArmory, IActivityDispatch{

    @Resource
    private IActivityRepository activityRepository;


    @Override
    public boolean assembleActivitySku(Long sku) {
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(sku);
        cacheActivitySkuStockCount(sku,activitySkuEntity.getStockCount());
        /**
         *  @author:wenzhuo4657
            des: 以下两个查询方法均保证redis中有对应缓存，因此此处可使用。
        */
        activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        return true;
    }
    private void cacheActivitySkuStockCount(Long sku, Integer stockCount){
        String cacheKey= Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY+sku;
        activityRepository.cacheActivitySkuStockCount(cacheKey,stockCount);
    }

    @Override
    public boolean subtractionActivitySkuStock(Long sku, Date endDateTime) {
        String cacheKey=Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY+sku;
        return activityRepository.subtractionActivitySkuStock(sku,cacheKey,endDateTime);
    }

    @Override
    public boolean assembleActivitySkuByActivityId(Long activityId) {
        List<ActivitySkuEntity> activitySkuEntities = activityRepository.queryActivitySkuListByActivityId(activityId);
        for (ActivitySkuEntity activitySkuEntity:activitySkuEntities){
            cacheActivitySkuStockCount(activitySkuEntity.getSku(), activitySkuEntity.getStockCountSurplus());

            activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        }
        activityRepository.queryRaffleActivityByActivityId(activityId);
        return true;
    }
}
