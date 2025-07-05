package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.BugleCaller;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.RaffleActivityOrder;

import java.util.List;

/**
 * 抽奖活动单(RaffleActivityOrder)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-17 09:12:07
 */

public interface RaffleActivityOrderDao extends BugleCaller {



    void insert(RaffleActivityOrder raffleActivityOrder);


    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);



    RaffleActivityOrder queryRaffleActivityOrder(RaffleActivityOrder raffleActivityOrderReq);

    int updateOrderCompleted(RaffleActivityOrder raffleActivityOrderReq);



    RaffleActivityOrder queryUnpaidActivityOrder(RaffleActivityOrder activityOrderReq);

    @Override
    List<Long> getId();
}

