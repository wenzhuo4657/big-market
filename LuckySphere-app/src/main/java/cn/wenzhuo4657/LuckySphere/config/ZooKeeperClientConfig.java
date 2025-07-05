package cn.wenzhuo4657.LuckySphere.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/19
 * @description:
 */
@Configuration
@EnableConfigurationProperties(ZookeeperClientConfigProperties.class)
public class ZooKeeperClientConfig {
    @Bean(name = "zookeeperClient")
    public CuratorFramework createWithOptions(ZookeeperClientConfigProperties properties){
        ExponentialBackoffRetry exponentialBackoffRetry =
                new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(properties.getConnectString())
                .retryPolicy(exponentialBackoffRetry)
                .sessionTimeoutMs(properties.getSessionTimeoutMs())
                .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                .build();
        client.start();
        return  client;
    }

    @Bean(name = "dccValueBeanFactory")
    @ConditionalOnProperty(value = "zookeeper.sdk.config.enable", havingValue = "true", matchIfMissing = false)
    public DCCValueBeanFactory dccValueBeanFactory(CuratorFramework zookeeperClient) throws Exception {
        return new DCCValueBeanFactory(zookeeperClient);
    }
}
