package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import cn.wenzhuo4657.BigMarket.infrastructure.event.EventPublisher;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.TaskDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserAwardRecordDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
 */
@Slf4j
@Repository
public class AwardRepository {
    @Resource
    private TaskDao taskDao;
    @Resource
    private UserAwardRecordDao userAwardRecordDao;
    @Resource
    private DBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

}
