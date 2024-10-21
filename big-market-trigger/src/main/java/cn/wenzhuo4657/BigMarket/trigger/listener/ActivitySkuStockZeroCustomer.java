package cn.wenzhuo4657.BigMarket.trigger.listener;

import cn.wenzhuo4657.BigMarket.domain.activity.service.ISkuStock;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
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
 * @date: 2024/10/21
 * @description: 消费者监听-活动库存耗尽
 */

@Slf4j
@Component
public class ActivitySkuStockZeroCustomer {
    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;
    @Resource
    private ISkuStock skuStock;

    @RabbitListener(queuesToDeclare = @Queue(value = "activity_sku_stock_zero"))
    public void listener(String message){
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

            /**
             *  @author:wenzhuo4657
                des: 库存耗尽MQ消息由redis缓存消费失败发出，
            */
              //  wenzhuo TODO 2024/10/21 : 待做消费流程图
            skuStock.clearActivitySkuStock(sku);
            skuStock.clearQueueValue();
        } catch (Exception e) {
            log.error("监听活动sku库存消耗为0消息，消费失败 topic: {} message: {}", topic, message);
            throw new RuntimeException(e);
        }
    }

}
