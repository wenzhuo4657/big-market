package cn.wenzhuo4657.BigMarket.domain.activity.service;

import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityAccountEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.DeliveryOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.SkuRechargeEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description: 抽奖活动订单额度接口-用户维度管理抽奖次数
 */
public interface IRaffleActivityAccountQuotaService {
    /**
     * 创建 sku 账户充值订单，
     *
     * @param skuRechargeEntity 活动商品充值实体对象
     * @return 活动ID
     */
      //  wenzhuo TODO 2024/11/5 : 充值次数方面。 该方法存在问题，
    String createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity);

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
