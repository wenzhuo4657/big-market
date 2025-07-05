package cn.wenzhuo4657.LuckySphere.domain.rebate.event;

import cn.wenzhuo4657.LuckySphere.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description: 发送返利事件
 */
@Component
public class SendRebateMessageEvent extends BaseEvent<SendRebateMessageEvent.RebateMessage> {


    private String topic="send_rebate";
    @Override
    public EventMessage<RebateMessage> buildEventMessage(RebateMessage data) {
        return EventMessage.<RebateMessage>builder()
                /**
                 *  @author:wenzhuo4657
                    des:  此处订单id无需唯一， 保证分布式一致性的字段是其他字段。
                */
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RebateMessage {
        /** 用户ID */
        private String userId;
        /** 返利描述 */
        private String rebateDesc;
        /** 返利类型 */
        private String rebateType;
        /** 返利配置 */
        private String rebateConfig;
        /** 业务ID - 唯一ID，确保幂等 */
        private String bizId;
    }
}
