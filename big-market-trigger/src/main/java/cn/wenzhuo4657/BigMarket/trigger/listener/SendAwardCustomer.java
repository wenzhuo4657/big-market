package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.award.event.SendAwardMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.DistributeAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.award.service.IAwardService;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description: 奖品消费者
 *
 */

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "big-market-app",
        topic ="send_award")
public class SendAwardCustomer implements RocketMQListener<String> {


    private  String topic="send_award";

    @Resource
    private IAwardService awardService;

    @Override
    public void onMessage(String message) {
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
            throw new RuntimeException(e);
        }
    }


}
