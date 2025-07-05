package cn.wenzhuo4657.LuckySphere.domain.credit.model.aggregate;

import cn.wenzhuo4657.LuckySphere.domain.award.model.valobj.TaskStateVO;
import cn.wenzhuo4657.LuckySphere.domain.credit.event.CreditAdjustSuccessMessageEvent;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.entity.CreditOrderEntity;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.entity.TaskEntity;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.valobj.TradeNameVO;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.valobj.TradeTypeVO;
import cn.wenzhuo4657.LuckySphere.types.event.BaseEvent;
import cn.wenzhuo4657.LuckySphere.types.utils.RandomOrderIdUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description: 通用积分聚合对象
 * 对所有积分交易业务进行了收束操作，并将调整成功的操作结果作为任务taskEntity实体。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeAggregate {
    // 用户ID
    private String userId;
    // 积分账户实体
    private CreditAccountEntity creditAccountEntity;
    // 积分订单实体
    private CreditOrderEntity creditOrderEntity;
    private TaskEntity taskEntity;

    public static CreditAccountEntity createCreditAccountEntity(String userId, BigDecimal adjustAmount) {
        return CreditAccountEntity.builder().userId(userId).adjustAmount(adjustAmount).build();
    }

    public static CreditOrderEntity createCreditOrderEntity(String userId,
                                                            TradeNameVO tradeName,
                                                            TradeTypeVO tradeType,
                                                            BigDecimal tradeAmount,
                                                            String outBusinessNo) {
        return CreditOrderEntity.builder()
                .userId(userId)
                .orderId(RandomOrderIdUtils.getOrderId())
                .tradeName(tradeName)
                .tradeType(tradeType)
                .tradeAmount(tradeAmount)
                .outBusinessNo(outBusinessNo)
                .build();
    }

    public static TaskEntity createTaskEntity(String userId, String topic, String messageId, BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userId);
        taskEntity.setTopic(topic);
        taskEntity.setMessageId(messageId);
        taskEntity.setMessage(message);
        taskEntity.setState(TaskStateVO.create);
        return taskEntity;
    }

}
