package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.RaffleActivityCount;

/**
 * 抽奖活动次数配置表(RaffleActivityCount)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-17 08:42:56
 */
public interface RaffleActivityCountDao {


    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);
}

