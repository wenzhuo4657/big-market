package cn.wenzhuo4657.BigMarket.test;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/18
 * @description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingTransactionTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public  void test(){
        activityRepository.doSaveOrder(new CreateOrderAggregate());
    }


}