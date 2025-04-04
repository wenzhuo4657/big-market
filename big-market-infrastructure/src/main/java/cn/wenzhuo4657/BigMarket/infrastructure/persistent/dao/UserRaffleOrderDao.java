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
      //  wenzhuo TODO 2025/3/20 :  保证同用户同活动下，只有一个未使用活动订单，否则不允许插入，也就是是说不允许并发写入订单，

    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrderReq);


    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);

    @Override
    List<Long> getId();
}

