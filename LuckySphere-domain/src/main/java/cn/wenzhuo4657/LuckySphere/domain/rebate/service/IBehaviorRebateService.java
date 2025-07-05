package cn.wenzhuo4657.LuckySphere.domain.rebate.service;

import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorRebateOrderEntity;

import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description: 行为返利服务接口
 */
public interface IBehaviorRebateService {
    /**
     * 创建”行为动作“的入账订单
     *
     */
    List<String> createOrder(BehaviorEntity behaviorEntity);


    /**
     * 根据外部单号查询订单
     *
     * @param userId        用户ID
     * @param outBusinessNo 业务ID；签到则是日期字符串，支付则是外部的业务ID
     * @return 返利订单实体
     */
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);
}
