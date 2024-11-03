package cn.wenzhuo4657.BigMarket.domain.rebate.service;

import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.BehaviorEntity;

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
     * @param behaviorEntity 行为实体对象
     * @return 订单ID
     */
    List<String> createOrder(BehaviorEntity behaviorEntity);
}
