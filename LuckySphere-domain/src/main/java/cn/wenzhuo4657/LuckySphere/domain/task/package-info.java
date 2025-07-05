package cn.wenzhuo4657.LuckySphere.domain.task;

/**
 *  @author:wenzhuo4657
    des:
    该任务指的是发送奖品任务，目前来说是指mq发送奖品任务，
    但是在实际场景中，可能是通过其他rpc、http服务进行发送，
    这些任务可能会因为超时等原因而发送失败。此时为了不损失其
    他性能，就需要任务补偿。。
*/
