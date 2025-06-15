package cn.wenzhuo4657.BigMarket.test;


import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserRaffleOrderDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserRaffleOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 *该测试的主要任务是，方便开发阶段快速对数据库进行一些常规操作。（该测试十分有必要，因为本项目是分库分表）
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourcesTest {



    @Autowired
    private DataSource dataSource;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserRaffleOrderDao userRaffleOrderDao;

    /**
     * 事务测试
     */
    @Test
    public void transaction() throws SQLException {


        transactionTemplate.execute(status -> {
            try {
                UserRaffleOrder userRaffleOrder = new UserRaffleOrder();
                userRaffleOrder.setActivityId(1L);
                userRaffleOrder.setUserId("456461");
                userRaffleOrder.setOrderId("1");
                userRaffleOrder.setOrderTime(new Date());
                userRaffleOrder.setOrderState("1");
                userRaffleOrder.setCreateTime(new Date());
                userRaffleOrder.setUpdateTime(new Date());
                userRaffleOrder.setStrategyId(1L);
                userRaffleOrder.setActivityName("1");
                userRaffleOrderDao.insert(userRaffleOrder);
                throw  new RuntimeException();
            }catch (Exception e){
                log.error("回滚事务",e);
                status.setRollbackOnly();
            }
            return 1;
        });

    }






    @Test
    public void deleteAll() throws SQLException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.executeUpdate("delete from raffle_activity_order");
        statement.executeUpdate("delete from user_award_record");
        statement.executeUpdate("delete from user_behavior_rebate_order");
        statement.executeUpdate("delete from user_credit_order");
        statement.executeUpdate("delete from user_raffle_order");
        statement.executeUpdate("delete from task");
    }























}
