package cn.wenzhuo4657.BigMarket.trigger.job;


import cn.wenzhuo4657.BigMarket.domain.task.model.entity.TaskEntity;
import cn.wenzhuo4657.BigMarket.domain.task.service.ITaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description: 推送task任务表中的消息到mq
 */
@Slf4j
@Component
public class SendMessageTaskJob {

    @Resource
    private ITaskService taskService;
    @Resource
    private ThreadPoolExecutor executor;


    @Resource
    private RedissonClient redissonClient;

    /**
     *  @author:wenzhuo4657
        des:
     任务详情，查找task表中任务状态为fail或者creat时间超过6小时的任务记录进行重新发送
    */

    @XxlJob("SendMessageTaskJob_DB1")
    public  void exec_db01(){



        RLock lock=redissonClient.getLock("SendMessageTaskJob_DB1");
        boolean isLocked=false;
        try {
            isLocked = lock.tryLock(3, 0, TimeUnit.SECONDS);
            if (!isLocked) return;

                executor.execute(()->{
                    try {

                        List<TaskEntity> taskEntities = taskService.queryNoSendMessageTaskList();
                        if (taskEntities.isEmpty()) return;
                        for (TaskEntity taskEntity : taskEntities) {
                            executor.execute(()->{
                                try {
                                    taskService.sendMessage(taskEntity);
                                    taskService.updateTaskSendMessageCompleted(taskEntity.getUserId(), taskEntity.getMessageId());
                                } catch (Exception e) {
                                    /**
                                     *  @author:wenzhuo4657
                                        des:
                                     注意，对于任务补发机制而言，重要是是保证消息的成功发送，而并非消息的正确消费。
                                     且对于本系统中各种错误通过catch捕捉处理，实际上消息的正确消费，等价于不发生catch,且就是说肯能会存在捕捉了某种catch，导致任务补发完成，但任务并为成功消费。
                                    */
                                    log.error("定时任务，发送MQ消息失败 userId: {} topic: {}", taskEntity.getUserId(), taskEntity.getTopic());
                                    taskService.updateTaskSendMessageFail(taskEntity.getUserId(), taskEntity.getMessageId());
                                }
                            });
                        }
                    }finally {

                    }
                });


        } catch (Exception e) {
            log.error("定时任务，扫描MQ任务表发送消息失败。", e);
        }finally {
            if (isLocked){
                lock.unlock();
            }
        }
    }
}
