package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.DeliveryOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.wenzhuo4657.BigMarket.domain.credit.event.CreditAdjustSuccessMessageEvent;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/11
 * @description: 积分调整成功消息监听消费---》商品发货
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "big-market-app",
        topic ="credit_adjust_success")
public class CreditAdjustSuccessCustomer implements RocketMQListener<String> {

    private String topic="credit_adjust_success";
    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;


    @Override
    public void onMessage(String message) {
        try {
            log.info("监听积分账户调整成功消息，进行交易商品发货 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage>>() {
            }.getType());
            CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage data = eventMessage.getData();
            DeliveryOrderEntity entity = new DeliveryOrderEntity();
            entity.setUserId(data.getUserId());
            entity.setOutBusinessNo(data.getOutBusinessNo());
            raffleActivityAccountQuotaService.updateOrder(entity);
        } catch (AppException e) {
            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.warn("监听积分账户调整成功消息，进行交易商品发货，消费重复 topic: {} message: {}", topic, message, e);
                return;
            }
            throw e;
        } catch (Exception e) {
            log.error("监听积分账户调整成功消息，进行交易商品发货失败 topic: {} message: {}", topic, message, e);
            throw e;
        }
    }


}
