package cn.wenzhuo4657.LuckySphere.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

@Slf4j
@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class ThreadPoolConfig {

    /**
     *  @author:wenzhuo4657
        des:
     该线程目池前仅仅用处任务补偿，核心线程数量不能超过当前机器cpu核心的一半
    */
    @Bean
    @ConditionalOnMissingBean(ThreadPoolExecutor.class)
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties properties) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 实例化策略
        RejectedExecutionHandler handler;
        switch (properties.getPolicy()){
            case "AbortPolicy":
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case "DiscardPolicy":
                handler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            case "DiscardOldestPolicy":
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case "CallerRunsPolicy":
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            default:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
        }
        // 创建线程池
//        核心线程取较小值
        int corepoolesize=Runtime.getRuntime().availableProcessors()/2<=properties.getCorePoolSize()? Runtime.getRuntime().availableProcessors()/2:properties.getCorePoolSize();
        log.info("CPU核数{},手动配置：{}，最终选择：{}",Runtime.getRuntime().availableProcessors(),properties.getCorePoolSize(),corepoolesize);
        return new ThreadPoolExecutor(properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(properties.getBlockQueueSize()),
                Executors.defaultThreadFactory(),
                handler);
    }

}
