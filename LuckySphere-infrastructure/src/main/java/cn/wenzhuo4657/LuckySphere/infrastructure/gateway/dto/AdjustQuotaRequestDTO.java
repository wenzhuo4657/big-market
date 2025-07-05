package cn.wenzhuo4657.LuckySphere.infrastructure.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description: 调额请求对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdjustQuotaRequestDTO {

    /** 请求应用ID */
    private String appId;
    /** 请求应用Token */
    private String appToken;

    /** 用户ID；微信分配的唯一ID编码 */
    private String openid;
    /** 调增额度 */
    private Integer increaseQuota;

}

