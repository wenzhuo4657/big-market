package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityOrder;

import java.util.List;

/**
 * 抽奖活动单(RaffleActivityOrder)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-17 09:12:07
 */
@DBRouterStrategy(splitTable = true)
public interface RaffleActivityOrderDao {


    @DBRouter(key = "userId")
    void insert(RaffleActivityOrder raffleActivityOrder);


    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);


    @DBRouter
    RaffleActivityOrder queryRaffleActivityOrder(RaffleActivityOrder raffleActivityOrderReq);

    int updateOrderCompleted(RaffleActivityOrder raffleActivityOrderReq);


    @DBRouter
    RaffleActivityOrder queryUnpaidActivityOrder(RaffleActivityOrder activityOrderReq);
}

