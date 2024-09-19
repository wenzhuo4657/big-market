package cn.wenzhuo4657.BigMarket.test;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.StrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @className: StrategyTest
 * @author: wenzhuo4657
 * @date: 2024/9/19 12:36
 * @Version: 1.0
 * @description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyTest {

    @Resource
    private StrategyArmory strategyArmory;

    @Test
    public  void test(){
        log.info("测试结果：{} - 奖品ID值", strategyArmory.getRandomAwardId(100002L));
    }
    }
}