package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;


import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserCreditOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户积分订单记录(UserCreditOrder000)表数据库访问层
 *
 * @author makejava
 * @since 2024-11-09 14:11:31
 */
@DBRouterStrategy(splitTable = true)
public interface UserCreditOrderDao {
    void insert(UserCreditOrder userCreditOrderReq);
}

