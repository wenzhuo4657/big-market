package cn.wenzhuo4657.BigMarket.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.TaskEntity;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.BehaviorTypeVO;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import cn.wenzhuo4657.BigMarket.domain.rebate.repository.IBehaviorRebateRepository;
import cn.wenzhuo4657.BigMarket.infrastructure.event.EventPublisher;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.DailyBehaviorRebateDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.TaskDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao.UserBehaviorRebateOrderDao;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.DailyBehaviorRebate;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.Task;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.UserBehaviorRebateOrder;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/4
 * @description: 行为返利仓储实现
 */
@Slf4j
@Repository
public class BehaviorRebateRepository implements IBehaviorRebateRepository {

    @Resource
    private DailyBehaviorRebateDao dailyBehaviorRebateDao;
    @Resource
    private UserBehaviorRebateOrderDao userBehaviorRebateOrderDao;
    @Resource
    private TaskDao taskDao;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

    @Override
    public List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO) {
        List<DailyBehaviorRebate> dailyBehaviorRebates = dailyBehaviorRebateDao.queryDailyBehaviorRebateByBehaviorType(behaviorTypeVO.getCode().toLowerCase());
        ArrayList<DailyBehaviorRebateVO> vos = new ArrayList<>(dailyBehaviorRebates.size());
        for (DailyBehaviorRebate vo:dailyBehaviorRebates){
            vos.add(DailyBehaviorRebateVO.builder()
                    .behaviorType(vo.getBehaviorType())
                    .rebateDesc(vo.getRebateDesc())
                    .rebateType(vo.getRebateType())
                    .rebateConfig(vo.getRebateConfig())
                    .build());
        }
        return vos;
    }

    @Override
    public void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates) {
        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {
                    for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
                        BehaviorRebateOrderEntity behaviorRebateOrderEntity = behaviorRebateAggregate.getBehaviorRebateOrderEntity();
                        // 用户行为返利订单对象
                        UserBehaviorRebateOrder userBehaviorRebateOrder = new UserBehaviorRebateOrder();
                        userBehaviorRebateOrder.setUserId(behaviorRebateOrderEntity.getUserId());
                        userBehaviorRebateOrder.setOrderId(behaviorRebateOrderEntity.getOrderId());
                        userBehaviorRebateOrder.setBehaviorType(behaviorRebateOrderEntity.getBehaviorType());
                        userBehaviorRebateOrder.setRebateDesc(behaviorRebateOrderEntity.getRebateDesc());
                        userBehaviorRebateOrder.setRebateType(behaviorRebateOrderEntity.getRebateType());
                        userBehaviorRebateOrder.setRebateConfig(behaviorRebateOrderEntity.getRebateConfig());
                        userBehaviorRebateOrder.setBizId(behaviorRebateOrderEntity.getBizId());
                        userBehaviorRebateOrder.setOutBusinessNo(behaviorRebateOrderEntity.getOutBusinessNo());
                        userBehaviorRebateOrderDao.insert(userBehaviorRebateOrder);
                        // 任务对象
                        TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
                        Task task = new Task();
                        task.setUserId(taskEntity.getUserId());
                        task.setTopic(taskEntity.getTopic());
                        task.setMessageId(taskEntity.getMessageId());
                        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
                        task.setState(taskEntity.getState().getCode());
                        taskDao.insert(task);
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入返利记录，唯一索引冲突 userId: {}", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });

        } finally {
            dbRouter.clear();
        }

        for (BehaviorRebateAggregate aggregate:behaviorRebateAggregates){
            TaskEntity taskEntity = aggregate.getTaskEntity();
            Task task=new Task();
            task.setUserId(taskEntity.getUserId());
            task.setMessageId(taskEntity.getMessageId());
            try {
                eventPublisher.publish(taskEntity.getTopic(),taskEntity.getMessage());
                taskDao.updateTaskSendMessageCompleted(task);
            } catch (Exception e) {
                log.error("写入返利记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
                taskDao.updateTaskSendMessageFail(task);
            }
        }

    }

    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        UserBehaviorRebateOrder userBehaviorRebateOrderReq=new UserBehaviorRebateOrder();
        userBehaviorRebateOrderReq.setUserId(userId);
        userBehaviorRebateOrderReq.setOutBusinessNo(outBusinessNo);
        List<UserBehaviorRebateOrder> userBehaviorRebateOrders =userBehaviorRebateOrderDao.queryOrderByOutBusinessNo(userBehaviorRebateOrderReq);
        List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = new ArrayList<>(userBehaviorRebateOrders.size());
        for (UserBehaviorRebateOrder userBehaviorRebateOrder : userBehaviorRebateOrders) {
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(userBehaviorRebateOrder.getUserId())
                    .orderId(userBehaviorRebateOrder.getOrderId())
                    .behaviorType(userBehaviorRebateOrder.getBehaviorType())
                    .rebateDesc(userBehaviorRebateOrder.getRebateDesc())
                    .rebateType(userBehaviorRebateOrder.getRebateType())
                    .rebateConfig(userBehaviorRebateOrder.getRebateConfig())
                    .outBusinessNo(userBehaviorRebateOrder.getOutBusinessNo())
                    .bizId(userBehaviorRebateOrder.getBizId())
                    .build();
            behaviorRebateOrderEntities.add(behaviorRebateOrderEntity);
        }
        return behaviorRebateOrderEntities;
    }
}
