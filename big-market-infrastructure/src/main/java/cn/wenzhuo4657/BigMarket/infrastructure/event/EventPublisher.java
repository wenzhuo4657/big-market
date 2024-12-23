package cn.wenzhuo4657.BigMarket.infrastructure.event;

import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
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
}
