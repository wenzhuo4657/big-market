package cn.wenzhuo4657.BigMarket.test;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.abstractbStrategyArmory
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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
@ImportAutoConfiguration(classes = {cn.wenzhuo4657.BigMarket.types.common.Constants.RedisKey.class})
public class StrategyTest {

    @Resource
    private abstractStrategyArmory strategyArmory;
    @Test
    public void test_strategyArmory() {
        boolean success = strategyArmory.assembleLotteryStrategy(100001L);
        log.info("测试结果：{}", success);
    }

    @Test
    public  void test(){
        log.info("测试结果：{} - 奖品ID值", strategyArmory.getRandomAwardId(100001L));
    }

}