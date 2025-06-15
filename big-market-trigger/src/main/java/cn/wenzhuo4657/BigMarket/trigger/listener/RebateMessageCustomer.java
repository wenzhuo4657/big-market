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

//                    todo  返利订单，首先只需要走无需支付的接口，他不需要支付，所以创建订单无效的creditAdjustService.createOrder，
//                     但问题是，raffleActivityAccountQuotaService.createSkuRechargeOrder内部会遵循类似与抽奖订单的逻辑，必须顺序消费sku订单
//                    而且对于sku商品支付似乎也有问题，他首先是发放sku活动额度，然后才是扣减积分，

                    UnpaidActivityOrderEntity skuRechargeOrder = raffleActivityAccountQuotaService.createSkuRechargeOrder(skuRechargeEntity);

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
