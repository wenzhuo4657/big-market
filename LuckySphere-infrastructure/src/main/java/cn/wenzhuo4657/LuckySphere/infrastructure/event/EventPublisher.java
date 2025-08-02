package cn.wenzhuo4657.LuckySphere.infrastructure.event;

import cn.wenzhuo4657.LuckySphere.domain.rebate.event.SendRebateMessageEvent;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.TaskDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.Task;
import cn.wenzhuo4657.LuckySphere.types.event.BaseEvent;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: rabbit事件
 */
@Slf4j
@Component
public class EventPublisher {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    TaskDao taskDao;

    /**
     *  @author:wenzhuo4657
        des: 发送消息到topic主题
    */
    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage){
        try{
            String messageJson= JSON.toJSONString(eventMessage);
            rocketMQTemplate.convertAndSend(topic,messageJson);
            log.info("发送MQ消息，topic:{} message:{}",topic,messageJson);

        } catch (Exception e) {
            log.info("发送MQ消息失败，topic:{} message:{}",topic,JSON.toJSONString(eventMessage));
            throw new RuntimeException(e);
        }
    }


    public  void publish(String topic,String eventMessageJSON){
        try {
            rocketMQTemplate.convertAndSend(topic,eventMessageJSON);
            log.info("发送MQ消息 topic:{} message:{}", topic, eventMessageJSON);
        }catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, eventMessageJSON, e);
            throw e;
        }



    }

    /**
     *  异步发送消息，避免阻塞
     */
    public void publish(String topic, BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage, Task task) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            rocketMQTemplate.asyncSend(topic, messageJson, new org.apache.rocketmq.client.producer.SendCallback() {
                @Override
                public void onSuccess(org.apache.rocketmq.client.producer.SendResult sendResult) {
                    taskDao.updateTaskSendMessageCompleted(task);
                    log.info("异步发送MQ消息成功，topic:{} message:{} msgId:{}", topic, messageJson, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("异步发送MQ消息失败 userId: {} topic: {}", task.getUserId(), task.getTopic(), throwable);
                    taskDao.updateTaskSendMessageFail(task);
                }
            });
        } catch (Exception e) {
            log.error("准备发送MQ消息时发生异常 userId: {} topic: {}", task.getUserId(), task.getTopic(), e);
            throw new RuntimeException(e);
        }
    }
}
