package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.BugleCaller;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserRaffleOrder;

import java.util.List;

/**
 * 用户抽奖订单表(UserRaffleOrder000)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:08:59
 */

public interface UserRaffleOrderDao extends BugleCaller {

    void insert(UserRaffleOrder userRaffleOrder);

    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrderReq);


    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);

    @Override
    List<Long> getId();
}

