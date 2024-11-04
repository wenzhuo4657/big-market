package cn.wenzhuo4657.BigMarket.domain.rebate.model.aggregate;

import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.BehaviorRebateOrderEntity;

import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description: 行为返利聚合根
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorRebateAggregate {

    /** 用户ID */
    private String userId;
    /** 行为返利订单实体对象 */
    private BehaviorRebateOrderEntity behaviorRebateOrderEntity;
    /** 任务实体对象 */
    private TaskEntity taskEntity;

}
