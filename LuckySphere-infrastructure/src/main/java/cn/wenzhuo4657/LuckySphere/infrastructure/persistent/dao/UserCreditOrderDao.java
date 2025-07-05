package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;


import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.BugleCaller;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserCreditOrder;

import java.util.List;

/**
 * 用户积分订单记录(UserCreditOrder000)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-09 14:11:31
 */
public interface UserCreditOrderDao extends BugleCaller {


    void insert(UserCreditOrder userCreditOrderReq);

    @Override
    List<Long> getId();
}

