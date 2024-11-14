package cn.wenzhuo4657.BigMarket.tigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/12
 * @description: 积分商品购物车对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuProductShopCartRequestDTO {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * sku 商品
     */
    private Long sku;
}
