package cn.wenzhuo4657.BigMarket.domain.rebate.model.entity;

import cn.wenzhuo4657.BigMarket.domain.rebate.event.SendRebateMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.TaskStateVO;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description: 任务实体对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
    /** 活动ID */
    private String userId;
    /** 消息主题 */
    private String topic;
    /** 消息编号 */
    private String messageId;
    /** 消息主体 */
    private BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> message;
    /** 任务状态；create-创建、completed-完成、fail-失败 */
    private TaskStateVO state;
}
