package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserBehaviorRebateOrder;

/**
 * 用户行为返利流水订单表(UserBehaviorRebateOrder)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-04 15:13:31
 */
@DBRouterStrategy(splitTable = true)
public interface UserBehaviorRebateOrderDao {

    void insert(UserBehaviorRebateOrder userBehaviorRebateOrder);
}

