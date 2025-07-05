package cn.wenzhuo4657.LuckySphere.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @className: RedisConfigProperties
 * @author: wenzhuo4657
 * @date: 2024/9/19 8:35
 * @Version: 1.0
 * @description:    redis连接参数读取
 */
@Data
@ConfigurationProperties(prefix = "redis.sdk.config", ignoreInvalidFields = true)
public class RedisClientConfigProperties {

    private String host;
    private int port;
    private int poolSize;
    private int minIdleSize;
    private  String password;


}
