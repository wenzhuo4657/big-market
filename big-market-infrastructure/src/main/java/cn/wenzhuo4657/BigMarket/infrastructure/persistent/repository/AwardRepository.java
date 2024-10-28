package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.wenzhuo4657.BigMarket.domain.award.model.aggregate.UserAwardRecordAggregate;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.TaskEntity;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.UserAwardRecordEntity;
import cn.wenzhuo4657.BigMarket.domain.award.repository.IAwardRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.event.EventPublisher;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.TaskDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserAwardRecordDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.Task;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserAwardRecord;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
 */
@Slf4j
@Repository
public class AwardRepository implements IAwardRepository {
    @Resource
    private TaskDao taskDao;
    @Resource
    private UserAwardRecordDao userAwardRecordDao;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

    @Override
    public void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate) {
        UserAwardRecordEntity userAwardRecordEntity = userAwardRecordAggregate.getUserAwardRecordEntity();
        TaskEntity taskEntity = userAwardRecordAggregate.getTaskEntity();
        String userId = userAwardRecordEntity.getUserId();
        Long activityId = userAwardRecordEntity.getActivityId();
        Integer awardId = userAwardRecordEntity.getAwardId();


        UserAwardRecord userAwardRecord = new UserAwardRecord();
        Task task=new Task();
        try {
            BeanUtils.copyProperties(userAwardRecord,userAwardRecordEntity);
            BeanUtils.copyProperties(task,taskEntity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }


        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {
                    // 写入记录
                    userAwardRecordDao.insert(userAwardRecord);
                    // 写入任务
                    taskDao.insert(task);
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入中奖记录，唯一索引冲突 userId: {} activityId: {} awardId: {}", userId, activityId, awardId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }

            });
        }finally {
            dbRouter.clear();
        }

        try {
            eventPublisher.publish(task.getTopic(),task.getMessage());
            taskDao.updateTaskSendMessageCompleted(task);
        } catch (Exception e) {
            log.error("写入中奖记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }


    }
}
