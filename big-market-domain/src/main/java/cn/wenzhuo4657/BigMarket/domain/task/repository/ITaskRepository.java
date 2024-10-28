package cn.wenzhuo4657.BigMarket.domain.task.repository;

import cn.wenzhuo4657.BigMarket.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description: 任务仓库
 */
public interface ITaskRepository {
    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);
}
