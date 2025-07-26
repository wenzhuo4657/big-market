package cn.wenzhuo4657.LuckySphere.config;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import static com.alibaba.nacos.common.packagescan.util.ResourceUtils.getFile;

@Component
public class DataSourceConfig {

//    @Bean
//    public DataSource dataSource() throws IOException, SQLException {
//        return  YamlShardingSphereDataSourceFactory.createDataSource(getFile("classpath:\\META-INF\\sharding-databases-tables.yaml"));
//    }
}
