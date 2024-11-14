package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.award.event.SendAwardMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.DistributeAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.award.service.IAwardService;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description: 奖品消费者
 */

@Slf4j
@Component
public class SendAwardCustomer {
    @Value("${spring.rabbitmq.topic.send_award}")
    private  String topic;

    @Resource
    private IAwardService awardService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_award}"))
    public void listener(String message){
        try {

            log.info("监听用户奖品发送消息 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage>>() {
            }.getType());
            SendAwardMessageEvent.SendAwardMessage data = eventMessage.getData();

            DistributeAwardEntity distributeAwardEntity=new DistributeAwardEntity();
            distributeAwardEntity.setUserId(data.getUserId());
            distributeAwardEntity.setOrderId(data.getOrderId());
            distributeAwardEntity.setAwardId(data.getAwardId());
            distributeAwardEntity.setAwardConfig(data.getAwardConfig());
            awardService.distributeAward(distributeAwardEntity);

        } catch (Exception e) {
            log.error("监听用户奖品发送消息，消费失败 topic: {} message: {}", topic, message);
//            throw e;

        }

    }
}
