package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

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


    void updateTaskSendMessageCompleted(Task task);


    void updateTaskSendMessageFail(Task task);

    /**
     *  @author:wenzhuo4657
        des:
     查找任务状态为 1，fail(发送失败)2，creat经过1分钟的
    */
    List<Task> queryNoSendMessageTaskList();
}

