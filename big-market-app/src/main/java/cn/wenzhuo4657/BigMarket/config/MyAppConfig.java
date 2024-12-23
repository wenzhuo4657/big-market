package cn.wenzhuo4657.BigMarket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
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
}