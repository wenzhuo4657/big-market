package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserRaffleOrder;

/**
 * 用户抽奖订单表(UserRaffleOrder000)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:08:59
 */
@DBRouterStrategy(splitTable = true)
public interface UserRaffleOrderDao {

    void insert(UserRaffleOrder userRaffleOrder);

    @DBRouter
    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrderReq);


    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);
}

