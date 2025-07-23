package cn.wenzhuo4657.LuckySphere.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring")
public class MyAppConfig {
    private Map<String, String> apptoken;

    public Map<String, String> getApptoken() {
        return apptoken;
    }

    public void setApptoken(Map<String, String> apptoken) {
        this.apptoken =apptoken;
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "big-market");
    }
}
