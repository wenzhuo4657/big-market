package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;


import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.DailyBehaviorRebate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 日常行为返利活动配置(DailyBehaviorRebate)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-04 15:14:30
 */
@Mapper
public interface DailyBehaviorRebateDao {

    /**
     *  @author:wenzhuo4657
        des: 返回行为返利类型为open状态
    */
    List<DailyBehaviorRebate> queryDailyBehaviorRebateByBehaviorType(String behaviorType);
  

}

