package cn.wenzhuo4657.BigMarket;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyArmory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.StrategyArmoryDispatch;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@SpringBootApplication
@Configurable
@MapperScan("cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao")
@EnableScheduling
public class Application {
    @Autowired
    private IStrategyArmory strategyArmory;

    @Autowired
    private IStrategyDispatch strategyDispatch;

    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
