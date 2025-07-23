package cn.wenzhuo4657.LuckySphere.test;



import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.SystemDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.UserCreditAccountDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.UserRaffleOrderDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.System;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserCreditAccount;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserRaffleOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
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


    /**
     * 清空业务库表测试
     */

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


    /**
     * 唯一索引测试
     *
     * 测试结果： sharding-jdbc不会将单表的唯一索引应用到分表结构上
     */
    @Test
    public  void  uniqueIndex() throws SQLException {
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
        long i=0;
        try {
            for (; i < 100; i++){
                userRaffleOrder.setId(i);
                userRaffleOrderDao.insert(userRaffleOrder);
            }

        } catch (DuplicateKeyException e) {
           log.info("插入次数i= {}",i);
        }


    }



    @Autowired
    private SystemDao systemDao;
    @Test
    public void insertexternal_system() throws SQLException {
        System system = new System();
        system.setId(UUID.randomUUID().toString());
        system.setSystemId("baiduqianfan");
        systemDao.insertSystem(system);
    }

    @Autowired
    UserCreditAccountDao userCreditAccountDao;
    @Test
    public void insertUserAccount() throws SQLException {
//        todo 搞清楚shard-jdbc是如何管理自增id，如果将自增id作为分片列，是否还可以
//        todo 自增逻辑修复  1，数据库userid字段长度改为100，2，代码层面路由id逻辑修改，且注意，由于数据库自增会覆盖代码的赋值，所以只需要考虑分片均匀
        UserCreditAccount open =new UserCreditAccount();
        open.setId(1l);
        open.setUserId("baidu@c5c8e75a-6215-4a51-8ace-1fdb404e3653");
        open.setAccountStatus("open");
        open.setAvailableAmount(new BigDecimal(0));
        open.setTotalAmount(new BigDecimal(0));
        open.setCreateTime(new Date());
        open.setUpdateTime(new Date());
        userCreditAccountDao.insert(open);
    }

























}
