package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.SkuRechargeEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.OrderTradeTypeVO;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.TradeEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.valobj.TradeNameVO;
import cn.wenzhuo4657.BigMarket.domain.credit.model.valobj.TradeTypeVO;
import cn.wenzhuo4657.BigMarket.domain.credit.service.ICreditAdjustService;
import cn.wenzhuo4657.BigMarket.domain.rebate.event.SendRebateMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.task.service.ITaskService;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/4
 * @description: 返利消息监听
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "big-market-app",
        topic ="send_rebate")
public class RebateMessageCustomer implements RocketMQListener<String> {

    private String topic="send_rebate";

    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;

    @Resource
    private ICreditAdjustService creditAdjustService;
    @Resource
    private ITaskService taskService;

    @Override
    public void onMessage(String message) {
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
                    skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.rebate_no_pay_trade);
                    raffleActivityAccountQuotaService.createSkuRechargeOrder(skuRechargeEntity);
                };break;
                case "integral":{
                    TradeEntity tradeEntity = new TradeEntity();
                    tradeEntity.setUserId(messageData.getUserId());
                    tradeEntity.setTradeName(TradeNameVO.REBATE);
                    tradeEntity.setTradeType(TradeTypeVO.FORWARD);
                    tradeEntity.setAmount(new BigDecimal(messageData.getRebateConfig()));
                    tradeEntity.setOutBusinessNo(messageData.getBizId());
                    //  wenzhuo TODO 2024/12/14 : 如果想要将返利流程融入，则需要将返利作为奖品写入，将其视为商品订单，
                    creditAdjustService.saveIntegralRebateOrder(tradeEntity);
                };break;
            }


        } catch (AppException e) {
            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.warn("监听用户行为返利消息，消费重复 topic: {} message: {}", topic, message, e);
                return;
            }
        } catch (Exception e) {
            log.error("监听用户行为返利消息，消费失败 topic: {} message: {}", topic, message, e);
            throw e;
        }
    }


}
