package cn.wenzhuo4657.BigMarket.domain.award.model.entity;

import cn.wenzhuo4657.BigMarket.domain.award.event.SendAwardMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.award.model.valobj.TaskStateVO;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
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
    private BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> message;
    /** 任务状态；create-创建、completed-完成、fail-失败 */
    private TaskStateVO state;
}
