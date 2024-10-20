package cn.wenzhuo4657.BigMarket.domain.activity.evnet;

import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import lombok.Value;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description:
 */
public class ActivitySkuStockZeroMessageEvent extends BaseEvent {

    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;
    @Override
    public EventMessage buildEventMessage(Object data) {
        return null;
    }

    @Override
    public String topic() {
        return null;
    }
}
