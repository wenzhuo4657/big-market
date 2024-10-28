package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityAccountDay;

/**
 * 抽奖活动账户表-日次数(RaffleActivityAccountDay)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:06:12
 */
public interface RaffleActivityAccountDayDao {

    void insert(RaffleActivityAccountDay raffleActivityAccountDay);
    @DBRouter
    RaffleActivityAccountDay queryActivityAccountDayByUserId(RaffleActivityAccountDay raffleActivityAccountDayReq);

    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccountDay build);
}

