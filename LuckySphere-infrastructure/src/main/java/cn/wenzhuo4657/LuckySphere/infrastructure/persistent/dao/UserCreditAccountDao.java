package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.BugleCaller;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserCreditAccount;

import java.util.List;

/**
 * 用户积分账户(UserCreditAccount)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-09 09:22:11
 */
public interface UserCreditAccountDao extends BugleCaller {


    void insert(UserCreditAccount userCreditAccountReq);


    int updateAddAmount(UserCreditAccount userCreditAccountReq);


    UserCreditAccount queryUserCreditAccount(UserCreditAccount userCreditAccountReq);

    @Override
    List<Long> getId();
}

