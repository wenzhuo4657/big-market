package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.SkuRechargeEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.TradeEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.valobj.TradeNameVO;
import cn.wenzhuo4657.BigMarket.domain.credit.model.valobj.TradeTypeVO;
import cn.wenzhuo4657.BigMarket.domain.credit.service.ICreditAdjustService;
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
import java.math.BigDecimal;

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

    @Resource
    private ICreditAdjustService creditAdjustService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message){
        try {
            log.info("监听用户行为返利消息 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>   eventMessage =
                    JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {}.getType());
            SendRebateMessageEvent.RebateMessage messageData = eventMessage.getData();

            switch (messageData.getRebateType()){
                case "sku":{
                    SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
                    skuRechargeEntity.setUserId(messageData.getUserId());
                    skuRechargeEntity.setSku(Long.valueOf(messageData.getRebateConfig()));
                    skuRechargeEntity.setOutBusinessNo(messageData.getBizId());
                    raffleActivityAccountQuotaService.createSkuRechargeOrder(skuRechargeEntity);
                };break;
                case "integral":{
                    TradeEntity tradeEntity = new TradeEntity();
                    tradeEntity.setUserId(messageData.getUserId());
                    tradeEntity.setTradeName(TradeNameVO.REBATE);
                    tradeEntity.setTradeType(TradeTypeVO.FORWARD);
                    tradeEntity.setAmount(new BigDecimal(messageData.getRebateConfig()));
                    tradeEntity.setOutBusinessNo(messageData.getBizId());
                    creditAdjustService.createOrder(tradeEntity);
                };break;
            }


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
