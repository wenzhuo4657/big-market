package cn.wenzhuo4657.BigMarket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description: Retrofit配置注入
 */
@Data
@ConfigurationProperties(prefix = "gateway.config", ignoreInvalidFields = true)
public class Retrofit2ConfigProperties {
    /** 状态；open = 开启、close 关闭 */
    private boolean enable;
    /** 转发地址 */
    private String apiHost;

}
