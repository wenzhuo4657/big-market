package cn.wenzhuo4657.BigMarket.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
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


    @Bean(name = "appTokenMap")
    public Map<String, String> appTokenMap() {

          //  wenzhuo TODO 2024/12/6 : 暂时写死，待优化
        HashMap<String, String> map = new HashMap<>();
        map.put("chatgpt-data","89iu7o8732ijd9114");
        return map;
    }

}
