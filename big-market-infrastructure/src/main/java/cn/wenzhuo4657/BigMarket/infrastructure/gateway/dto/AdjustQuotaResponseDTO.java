package cn.wenzhuo4657.BigMarket.infrastructure.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description: 调额响应对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdjustQuotaResponseDTO {

    /**
     * 总量额度
     */
    private Integer totalQuota;
    /**
     * 剩余额度
     */
    private Integer surplusQuota;

}
