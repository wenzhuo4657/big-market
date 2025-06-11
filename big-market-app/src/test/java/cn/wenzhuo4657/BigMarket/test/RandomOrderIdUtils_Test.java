package cn.wenzhuo4657.BigMarket.test;


import cn.wenzhuo4657.BigMarket.types.utils.RandomOrderIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomOrderIdUtils_Test {



    @Test
    public  void test(){
        String orderIdByTime = RandomOrderIdUtils.getOrderIdByTime();
        log.info("orderIdByTime:{}",orderIdByTime);
    }

}
