package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.Task;

import java.util.List;

/**
 * 任务表，发送MQ(Task)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:08:15
 */
public interface TaskDao {

    void insert(Task task);

    @DBRouter
    void updateTaskSendMessageCompleted(Task task);

    @DBRouter
    void updateTaskSendMessageFail(Task task);

    List<Task> queryNoSendMessageTaskList();
}

