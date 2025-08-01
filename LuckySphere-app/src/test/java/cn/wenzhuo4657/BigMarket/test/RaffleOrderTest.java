package cn.wenzhuo4657.LuckySphere.test;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.SkuRechargeEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.UnpaidActivityOrderEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.IRaffleActivityAccountQuotaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleOrderTest {

    @Resource
    private IRaffleActivityAccountQuotaService raffleOrder;

    @Test
    public void test_createSkuRechargeOrder() throws InterruptedException {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("xiaofuge");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
        skuRechargeEntity.setOutBusinessNo("700091009111");
        UnpaidActivityOrderEntity orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
        log.info("测试结果：{}", orderId);
    }



}

