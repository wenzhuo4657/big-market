package cn.wenzhuo4657.LuckySphere.config;

import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.IOpenAIAccountService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description: Retrofit框架
 */
@Configuration
@EnableConfigurationProperties(Retrofit2ConfigProperties.class)
public class Retrofit2Config {

    @Bean
    public Retrofit  retrofit(Retrofit2ConfigProperties properties){
        return  new Retrofit.Builder()
                .baseUrl(properties.getApiHost())
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    @Bean
    public IOpenAIAccountService weixinApiService(Retrofit retrofit){
        return retrofit.create(IOpenAIAccountService.class);
    }
}
