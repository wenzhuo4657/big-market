package cn.wenzhuo4657.LuckySphere.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 活动购物车实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityShopCartEntity {
    /** 用户ID */
    private String userId;
    /** 商品SKU - activity + activity count */
    private Long sku;
}
