package cn.wenzhuo4657.BigMarket.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.Kryo5Codec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @className: RedisClientConfig
 * @author: wenzhuo4657
 * @date: 2024/9/19 8:41
 * @Version: 1.0
 * @description: redis客户端，使用redisson单实例模式连接
 * 参考：https://github.com/redisson/redisson/wiki/2.-Configuration/#26-single-instance-mode
 */
@Configuration
@EnableConfigurationProperties(value = {RedisClientConfigProperties.class})
@Slf4j
public class RedisClientConfig {

    @Bean("redissonClient")
    @Primary
    public RedissonClient redissonClient(ConfigurableApplicationContext applicationContext,RedisClientConfigProperties configProperties){
        Config config=new Config();
        config.useSingleServer()
                        .setAddress("redis://"+configProperties.getHost()+":"+configProperties.getPort())
                                .setConnectionPoolSize(configProperties.getPoolSize())
                                        .setConnectionMinimumIdleSize(configProperties.getMinIdleSize());

//        config.setCodec(new Kryo5Codec());
        config.setCodec(JsonJacksonCodec.INSTANCE);
        return Redisson.create(config);
    }

//    @Bean("redissonClient")
//    public RedissonClient redissonClient(DynamicThreadPoolAutoProperties properties) {
//        Config config = new Config();
//        config.setCodec(JsonJacksonCodec.INSTANCE);
//        config.useSingleServer()
//                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
//                .setPassword(properties.getPassword())
//                .setConnectionPoolSize(properties.getPoolSize())
//                .setConnectionMinimumIdleSize(properties.getMinIdleSize())
//                .setIdleConnectionTimeout(properties.getIdleTimeout())
//                .setConnectTimeout(properties.getConnectTimeout())
//                .setRetryAttempts(properties.getRetryAttempts())
//                .setRetryInterval(properties.getRetryInterval())
//                .setPingConnectionInterval(properties.getPingInterval())
//                .setKeepAlive(properties.isKeepAlive())
//                .setDatabase(properties.getDatabaseIndex())
//        ;
//        RedissonClient redissonClient = Redisson.create(config);
//
//        log.info("动态线程池，注册器（redis）链接初始化完成。{} {} {}", properties.getHost(), properties.getPoolSize(), !redissonClient.isShutdown());
//
//        return redissonClient;
//    }



      /**
         *  des: 查看源码得知，redisson框架内部序列化注入类的特点和关键方法如下
       *  public class XXXCodec extends BaseCodec {
       *        private final Decoder<Object> decoder;
       *        private final Encoder encoder;
       *      public Decoder<Object> getValueDecoder() {
       *         return this.decoder;
       *     }
       *
       *     public Encoder getValueEncoder() {
       *         return this.encoder;
       *     }
       *
       *  }
       *  其中baseCodec为抽象类，实现codec接口，对于其子类的重写方法并没有标准，
       *  对于reidsson框架来说定义了边界，而我在此处仅仅想要自定义编解码，所以理论上只需要重写上述两个方法。
       *
       *
       *
       *  该设计模式有点像访问者模式。定义了访问的边界，子类根据需要实现即可。
         * */
      public  class myCodec extends  BaseCodec{
          //  wenzhuo TODO 2024/9/19 : 自定义序列化方式
          @Override
          public Decoder<Object> getValueDecoder() {
              return null;
          }

          @Override
          public Encoder getValueEncoder() {
              return null;
          }
      }




}