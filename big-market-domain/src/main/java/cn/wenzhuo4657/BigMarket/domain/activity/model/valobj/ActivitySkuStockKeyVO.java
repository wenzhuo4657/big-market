package cn.wenzhuo4657.BigMarket.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: 活动sku库存
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySkuStockKeyVO {
    /** 商品sku */
    private Long sku;
    /** 活动ID */
    private Long activityId;
}
