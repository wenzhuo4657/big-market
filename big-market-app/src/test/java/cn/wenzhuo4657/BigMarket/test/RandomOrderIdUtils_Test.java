package cn.wenzhuo4657.BigMarket.test;


import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserRaffleOrderDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserRaffleOrder;
import cn.wenzhuo4657.BigMarket.types.utils.RandomOrderIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomOrderIdUtils_Test {


    @Autowired
    private UserRaffleOrderDao userRaffleOrderDao;



    @Test
    public  void test(){
        String orderIdByTime = RandomOrderIdUtils.getOrderId();
        log.info("orderIdByTime:{}",orderIdByTime);
    }

    /**
     * UUID生成的长度测试
     * 目前长度为32
     */
    @Test
    public  void test1(){
        for (int i = 0; i < 100; i++){
            String orderIdByTime = RandomOrderIdUtils.getOrderIdByUUID();
            log.info("第{}次:{}",i,orderIdByTime.length());
        }

    }


    /**
     * UUID插入数据库测试
     */

    @Test
    public  void test2(){
        UserRaffleOrder userRaffleOrder = new UserRaffleOrder();
        userRaffleOrder.setActivityId(464564L);
        userRaffleOrder.setUserId("456461");
        userRaffleOrder.setOrderId(RandomOrderIdUtils.getOrderIdByUUID());
        userRaffleOrder.setOrderTime(new Date());
        userRaffleOrder.setOrderState("1");
        userRaffleOrder.setCreateTime(new Date());
        userRaffleOrder.setUpdateTime(new Date());
        userRaffleOrder.setStrategyId(1L);
        userRaffleOrder.setActivityName("1");
        userRaffleOrderDao.insert(userRaffleOrder);
    }

}
