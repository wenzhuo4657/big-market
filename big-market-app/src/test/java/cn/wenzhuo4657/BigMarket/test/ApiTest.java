package cn.wenzhuo4657.BigMarket.test;

import cn.wenzhuo4657.BigMarket.tigger.api.IRaffleActivityService;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.ActivityDrawRequestDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.ActivityDrawResponseDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.reponse.Response;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApiTest {

    @Test
    public void test_rpc() {
        Double dd=0.1-0.2;
        BigDecimal bigDecimal1 = new BigDecimal(dd);
        System.out.println(bigDecimal1);
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(dd));
        System.out.println(bigDecimal);


    }

}