package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivitySkuStockService;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/21
 * @description: 消费者监听-sku商品库存耗尽
 */

@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "big-market-app",
        topic ="activity_sku_stock_zero" )
public class ActivitySkuStockZeroCustomer implements RocketMQListener<String> {

    private String topic="activity_sku_stock_zero";
    @Resource
    private IRaffleActivitySkuStockService skuStock;

    @Override
    public void onMessage(String message) {
        try {
            log.info("监听活动sku库存消耗为0消息 topic: {} message: {}", topic, message);
            /**
             *  @author:wenzhuo4657
            des:
            new TypeReference<BaseEvent.EventMessage<Long>>() {
            }.getType()
            强制 Client 端创建此类的子类，即使在运行时也可以检索类型信息。
             */
            BaseEvent.EventMessage<Long> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<Long>>() {
            }.getType());

            Long sku = eventMessage.getData();
            skuStock.clearActivitySkuStock(sku);
            skuStock.clearQueueValue();
        } catch (Exception e) {
            log.error("监听活动sku库存消耗为0消息，消费失败 topic: {} message: {}", topic, message);
            throw new RuntimeException(e);
        }
    }



}
