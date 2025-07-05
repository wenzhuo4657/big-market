package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.BugleCaller;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.RaffleActivityAccountDay;

import java.util.List;

/**
 * 抽奖活动账户表-日次数(RaffleActivityAccountDay)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:06:12
 */
public interface RaffleActivityAccountDayDao  extends BugleCaller {

    void insert(RaffleActivityAccountDay raffleActivityAccountDay);

    RaffleActivityAccountDay queryActivityAccountDayByUserId(RaffleActivityAccountDay raffleActivityAccountDayReq);

    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccountDay build);

    /**
     *  @author:wenzhuo4657
        des:
     查询指定日期，用户，活动的日账户记录的已参与活动次数。
    */

    Integer queryRaffleActivityAccountDayPartakeCount(RaffleActivityAccountDay raffleActivityAccountDay);

    void addAccountQuota(RaffleActivityAccountDay raffleActivityAccountDay);

    @Override
    List<Long> getId();
}

