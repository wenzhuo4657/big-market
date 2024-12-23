package cn.wenzhuo4657.BigMarket.domain.activity.evnet;

import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description:
 */
@Component
public class ActivitySkuStockZeroMessageEvent extends BaseEvent<Long> {


    private String topic="activity_sku_stock_zero";
    @Override
    public EventMessage<Long> buildEventMessage(Long sku) {
        return EventMessage.<Long>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(sku)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }
}
