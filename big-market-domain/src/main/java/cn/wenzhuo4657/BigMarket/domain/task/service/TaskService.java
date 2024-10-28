package cn.wenzhuo4657.BigMarket.domain.task.service;

import cn.wenzhuo4657.BigMarket.domain.task.model.entity.TaskEntity;
import cn.wenzhuo4657.BigMarket.domain.task.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
 */
@Service
public class TaskService implements ITaskService{

    @Resource
    private ITaskRepository taskRepository;
    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        return taskRepository.queryNoSendMessageTaskList();
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        taskRepository.sendMessage(taskEntity);

    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        taskRepository.updateTaskSendMessageCompleted(userId, messageId);

    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        taskRepository.updateTaskSendMessageFail(userId, messageId);
    }
}
