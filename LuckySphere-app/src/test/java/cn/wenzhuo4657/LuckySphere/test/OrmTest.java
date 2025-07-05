package cn.wenzhuo4657.LuckySphere.test;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.AwardDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.Award;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrmTest {

    @Resource
    private AwardDao awardDao;



    @Test
    public void test1() {
        List<Award> awards = awardDao.queryAwardList();
        log.info("测试结果：{}", JSON.toJSONString(awards));
    }

}
