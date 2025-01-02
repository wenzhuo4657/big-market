package cn.wenzhuo4657.BigMarket.config;
import org.apache.dubbo.config.ApplicationConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaConfig {

    @Bean(name = "cache")
    public Cache<String, String> cache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build();
    }


    @Resource
    private  MyAppConfig myAppConfig;

    @Bean(name = "appTokenMap")
    public Map<String, String> appTokenMap() {
        return myAppConfig.getApptoken();
    }

}
