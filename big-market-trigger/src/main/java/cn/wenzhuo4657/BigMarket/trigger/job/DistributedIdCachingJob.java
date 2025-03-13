package cn.wenzhuo4657.BigMarket.trigger.job;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.redis.RedissonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2025/3/13
 * @description:
 */
@Component
public class DistributedIdCachingJob implements CommandLineRunner {
    @Resource
    private RedissonService redissonService;

    @Override
    public void run(String... args) throws Exception {

    }
}
