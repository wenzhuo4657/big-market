package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserAwardRecord;

/**
 * 用户中奖记录表(UserAwardRecord000)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:08:38
 */

public interface UserAwardRecordDao {


    void insert(UserAwardRecord userAwardRecord);



    int updateAwardRecordCompletedState(UserAwardRecord userAwardRecordReq);
}

