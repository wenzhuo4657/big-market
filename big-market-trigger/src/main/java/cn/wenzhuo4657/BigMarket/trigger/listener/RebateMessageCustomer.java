package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.SkuRechargeEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.UnpaidActivityOrderEntity;
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
@RocketMQMessageListener(
        consumerGroup = "big-market-app-Customer-send_rebate",
        topic ="send_rebate"
        )
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

//                    todo  等待厘清sku商品订单的逻辑，createSkuRechargeOrder这个是方法的逻辑目前似乎不适合返利，
//                    目前以下的两个方法的逻辑借鉴创建sku订单，最终是要发送给credit_adjust_success来进行监听消息的，这样做似乎有点臃肿？
                    UnpaidActivityOrderEntity skuRechargeOrder = raffleActivityAccountQuotaService.createSkuRechargeOrder(skuRechargeEntity);
                    String orderId = creditAdjustService.createOrder(TradeEntity.builder()
                            .userId(skuRechargeOrder.getUserId())
                            .tradeName(TradeNameVO.CONVERT_SKU)
                            .tradeType(TradeTypeVO.REVERSE)
                            .amount(skuRechargeOrder.getPayAmount())
                            .outBusinessNo(skuRechargeOrder.getOutBusinessNo())
                            .build());
                };break;
                case "integral":{
                    TradeEntity tradeEntity = new TradeEntity();
                    tradeEntity.setUserId(messageData.getUserId());
                    tradeEntity.setTradeName(TradeNameVO.REBATE);
                    tradeEntity.setTradeType(TradeTypeVO.FORWARD);
                    tradeEntity.setAmount(new BigDecimal(messageData.getRebateConfig()));
                    tradeEntity.setOutBusinessNo(messageData.getBizId());
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
