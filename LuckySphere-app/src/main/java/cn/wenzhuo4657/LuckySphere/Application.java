package cn.wenzhuo4657.LuckySphere;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.alibaba.nacos.common.packagescan.util.ResourceUtils.getFile;

@SpringBootApplication
@Configurable
@MapperScan("cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao")
@EnableScheduling
@EnableDubbo
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {
//    todo 目前并不清楚tomcat如何启动，需要实现，只有所有准备完成（各种中间件连接、测试完毕），才会接受外部的流量，否则可能会出现未知的错误。

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);


    }

    
}
