package cn.wenzhuo4657.BigMarket;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyArmory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

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
