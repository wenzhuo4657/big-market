package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.repository;

import cn.wenzhuo4657.LuckySphere.domain.award.model.aggregate.GiveOutPrizesAggregate;
import cn.wenzhuo4657.LuckySphere.domain.award.model.aggregate.UserAwardRecordAggregate;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.TaskEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserAwardRecordEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserCreditAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.valobj.AccountStatusVO;
import cn.wenzhuo4657.LuckySphere.domain.award.repository.IAwardRepository;
import cn.wenzhuo4657.LuckySphere.infrastructure.event.EventPublisher;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.*;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.Task;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserAwardRecord;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserCreditAccount;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserRaffleOrder;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.redis.RedissonService;
import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.dao.DuplicateKeyException;
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
public class AwardRepository implements IAwardRepository {
    @Resource
    private TaskDao taskDao;
    @Resource
    private UserAwardRecordDao userAwardRecordDao;

    @Resource
    private AwardDao awardDao;

    @Resource
    private UserRaffleOrderDao userRaffleOrderDao;

    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private UserCreditAccountDao userCreditAccountDao;

    @Resource
    private RedissonService redissonService;

    @Override
    public void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate) {
        UserAwardRecordEntity userAwardRecordEntity = userAwardRecordAggregate.getUserAwardRecordEntity();
        TaskEntity taskEntity = userAwardRecordAggregate.getTaskEntity();
        String userId = userAwardRecordEntity.getUserId();
        Long activityId = userAwardRecordEntity.getActivityId();
        Integer awardId = userAwardRecordEntity.getAwardId();
        String orderId = userAwardRecordEntity.getOrderId();


        UserAwardRecord userAwardRecord = new UserAwardRecord();
        userAwardRecord.setUserId(userAwardRecordEntity.getUserId());
        userAwardRecord.setActivityId(userAwardRecordEntity.getActivityId());
        userAwardRecord.setStrategyId(userAwardRecordEntity.getStrategyId());
        userAwardRecord.setOrderId(userAwardRecordEntity.getOrderId());
        userAwardRecord.setAwardId(userAwardRecordEntity.getAwardId());
        userAwardRecord.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        userAwardRecord.setAwardTime(userAwardRecordEntity.getAwardTime());
        userAwardRecord.setAwardState(userAwardRecordEntity.getAwardState().getCode());

        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());





        try {

            transactionTemplate.execute(status -> {
                try {

                    //                    更新抽奖单
                    int count = userRaffleOrderDao.updateUserRaffleOrderStateUsed(userId,activityId,orderId);



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

        }

          //  wenzhuo TODO 2025/1/23 : 待优化，使用rocketmq的事务消息，保证消息零丢失、实时性、最终一致性

        try {
            eventPublisher.publish(task.getTopic(),task.getMessage());
            taskDao.updateTaskSendMessageCompleted(task);
        } catch (Exception e) {
            log.error("写入中奖记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }


    }

    @Override
    public String queryAwardConfig(Integer awardId) {
        return  awardDao.queryAwardConfigByAwardId(awardId);
    }

    @Override
    public void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate) {
        String userId = giveOutPrizesAggregate.getUserId();
        UserAwardRecordEntity userAwardRecordEntity = giveOutPrizesAggregate.getUserAwardRecordEntity();
        UserCreditAwardEntity userCreditAwardEntity = giveOutPrizesAggregate.getUserCreditAwardEntity();


//        将实体转换为po
        UserAwardRecord userAwardRecord = new UserAwardRecord();
        userAwardRecord.setUserId(userId);
        userAwardRecord.setOrderId(userAwardRecordEntity.getOrderId());
        userAwardRecord.setAwardState(userAwardRecordEntity.getAwardState().getCode());

//    该数值表示增量，在执行总会去判断是否存在相应账户，如果不存在，则使用该增量作为账户插入
        UserCreditAccount userCreditAccountReq=new UserCreditAccount();
        userCreditAccountReq.setUserId(userCreditAwardEntity.getUserId());
        userCreditAccountReq.setTotalAmount(userCreditAwardEntity.getCreditAmount());
        userCreditAccountReq.setAvailableAmount(userCreditAwardEntity.getCreditAmount());
        userCreditAccountReq.setAccountStatus(AccountStatusVO.open.getCode());


        try {
            transactionTemplate.execute(status -> {
                try{
                    int updateAccountCount = userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    if (0==updateAccountCount){
                        userCreditAccountDao.insert(userCreditAccountReq);
                    }
                    int updateAwardCount = userAwardRecordDao.updateAwardRecordCompletedState(userAwardRecord);
                    if (updateAwardCount==0){
                        log.warn("更新中奖记录，重复更新拦截 userId:{} giveOutPrizesAggregate:{}", userId, JSON.toJSONString(giveOutPrizesAggregate));
                        status.setRollbackOnly();
                    }

                    return  1;

                }catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("更新中奖记录，唯一索引冲突 userId: {} ", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });
        }finally {
        }

    }

    @Override
    public void saveGiveOutPrizesAggregate(UserAwardRecordEntity userAwardRecord) {

        String userId = userAwardRecord.getUserId();
        UserAwardRecord userAwardRecordReq = new UserAwardRecord();
        userAwardRecordReq.setUserId(userId);
        userAwardRecordReq.setOrderId(userAwardRecord.getOrderId());
        userAwardRecordReq.setAwardState(userAwardRecord.getAwardState().getCode());
        try {
            transactionTemplate.execute(status -> {
                try{

                    int updateAwardCount = userAwardRecordDao.updateAwardRecordCompletedState(userAwardRecordReq);
                    if (updateAwardCount==0){
                        log.warn("更新中奖记录，重复更新拦截 userId:{} giveOutPrizesAggregate:{}", userId, JSON.toJSONString(userAwardRecord));
                        status.setRollbackOnly();
                    }

                    return  1;

                }catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("更新中奖记录，唯一索引冲突 userId: {} ", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });
        }finally {

        }
    }

    @Override
    public String queryAwardKey(Integer awardId) {
        return awardDao.queryAwardKeyByAwardId(awardId);
    }
}
