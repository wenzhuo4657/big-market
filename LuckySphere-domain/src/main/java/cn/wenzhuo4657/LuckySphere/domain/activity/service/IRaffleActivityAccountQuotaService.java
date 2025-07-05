package cn.wenzhuo4657.LuckySphere.domain.activity.service;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.ActivityAccountEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.DeliveryOrderEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.SkuRechargeEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.UnpaidActivityOrderEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 抽奖活动订单额度接口-用户维度管理抽奖次数
 */
public interface IRaffleActivityAccountQuotaService {
    /**
     * 创建 sku 账户充值订单，（并未涉及到支付，消费订单）
     * ps:内部实际上是创建一个奖品订单，奖品为sku活动商品
     * @param skuRechargeEntity 活动商品充值实体对象
     * @return 未支付活动订单(会优先从数据库中查找)
     * ps:内部会通过trade贸易模块判断是否需要支付订单，但是请注意，无论如何该方法最后的终点是sku商品的发放
     */
    UnpaidActivityOrderEntity createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity);

    /**
     * 订单出货 - 积分充值
     * @param deliveryOrderEntity 出货单实体对象
     */
    void updateOrder(DeliveryOrderEntity deliveryOrderEntity);

    /**
     *  @author:wenzhuo4657
        des:
             查询用户抽奖剩余日次数
    */
    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);

    /**
     *  @author:wenzhuo4657
        des:
     查询用户抽奖剩余日额度
    */
    Integer queryRaffleActivityAccountPartakeCount(String userId, Long activityId);

    /**
     * 查询活动账户额度「总、月、日」
     *
     * @param activityId 活动ID
     * @param userId     用户ID
     * @return 账户实体
     */
    ActivityAccountEntity queryActivityAccountEntity(Long activityId, String userId);
    /**
     * 查询活动账户 - 总，参与次数
     *
     * @param activityId 活动ID
     * @param userId     用户ID
     * @return 参与次数
     */
    Integer queryRaffleActivityAccountPartakeCount(Long activityId, String userId);

}
