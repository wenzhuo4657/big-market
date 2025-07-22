package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.BugleCaller;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户抽奖订单表(UserRaffleOrder000)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:08:59
 */

public interface UserRaffleOrderDao extends BugleCaller {

    void insert(UserRaffleOrder userRaffleOrder);

    List<UserRaffleOrder> queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrderReq);


    int updateUserRaffleOrderStateUsed(@Param("userId") String userId, @Param("activityId") Long activityId, @Param("orderId") String orderId);

    @Override
    List<Long> getId();
}

