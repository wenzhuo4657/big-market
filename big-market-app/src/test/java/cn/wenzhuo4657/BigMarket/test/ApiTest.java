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
        int a=0;
        System.out.println(-1*a);


    }

}