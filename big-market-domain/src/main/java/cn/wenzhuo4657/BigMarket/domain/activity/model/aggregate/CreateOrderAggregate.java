package cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate;


import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityAccountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    /**
     * 活动账户实体
     */
    private ActivityAccountEntity activityAccountEntity;
    /**
     * 活动订单实体
     */
    private ActivityOrderEntity activityOrderEntity;

}