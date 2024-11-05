package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityAccountMonth;

/**
 * 抽奖活动账户表-月次数(RaffleActivityAccountMonth)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:07:43
 */
public interface RaffleActivityAccountMonthDao {


    @DBRouter
    RaffleActivityAccountMonth queryActivityAccountMonthByUserId(RaffleActivityAccountMonth raffleActivityAccountMonthReq);

    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonth build);

    void insertActivityAccountMonth(RaffleActivityAccountMonth build);

    void addAccountQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);
}

