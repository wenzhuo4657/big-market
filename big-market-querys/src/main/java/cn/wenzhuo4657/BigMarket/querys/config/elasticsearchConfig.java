package cn.wenzhuo4657.BigMarket.querys.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "cn.wenzhuo4657.BigMarket.querys.repository")
@Configuration
public class elasticsearchConfig {

    @Bean
    public RestHighLevelClient elasticsearchClient() {

//        todo  生产环境下应当有安全策略，事实上，这一块应当使用nacos的动态导入配置文件
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("47.92.150.10:9200")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}