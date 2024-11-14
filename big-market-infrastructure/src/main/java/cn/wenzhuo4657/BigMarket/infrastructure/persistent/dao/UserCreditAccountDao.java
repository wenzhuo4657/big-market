package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserCreditAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户积分账户(UserCreditAccount)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-09 09:22:11
 */
public interface UserCreditAccountDao {

    void insert(UserCreditAccount userCreditAccountReq);

    int updateAddAmount(UserCreditAccount userCreditAccountReq);


    @DBRouter
    UserCreditAccount queryUserCreditAccount(UserCreditAccount userCreditAccountReq);
}

