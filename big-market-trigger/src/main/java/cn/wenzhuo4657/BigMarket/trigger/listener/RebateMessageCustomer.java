package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.SkuRechargeEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.wenzhuo4657.BigMarket.domain.rebate.event.SendRebateMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.RebateTypeVO;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/4
 * @description: 返利消息监听---》奖品消息接收并入库,目前仅仅是抽奖额度
 */
@Slf4j
@Component
public class RebateMessageCustomer {
    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;

    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message){
        try {
            log.info("监听用户行为返利消息 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>   eventMessage =
                    JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {}.getType());
            SendRebateMessageEvent.RebateMessage messageData = eventMessage.getData();
            if (!RebateTypeVO.SKU.getCode().equals(messageData.getRebateType())){
                //  wenzhuo TODO 2024/11/5 : 暂不处理非sku奖励
                log.info("监听用户行为返利消息 - 非sku奖励暂时不处理 topic: {} message: {}", topic, message);
                return;
            }
            SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
            skuRechargeEntity.setSku(Long.valueOf(messageData.getRebateConfig()));
            skuRechargeEntity.setUserId(messageData.getUserId());
            skuRechargeEntity.setOutBusinessNo(messageData.getBizId());
            raffleActivityAccountQuotaService.createSkuRechargeOrder(skuRechargeEntity);


        } catch (AppException e) {
            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.warn("监听用户行为返利消息，消费重复 topic: {} message: {}", topic, message, e);
                return;
            }
            throw e;
        } catch (Exception e) {
            log.error("监听用户行为返利消息，消费失败 topic: {} message: {}", topic, message, e);
            throw e;
        }

    }

}
