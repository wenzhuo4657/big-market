package cn.wenzhuo4657.BigMarket.domain.award.service.distribute;

import cn.wenzhuo4657.BigMarket.domain.award.model.entity.DistributeAwardEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/7
 * @description: 奖品分发接口
 */
public interface IDistributeAward {
    /**
     *  @author:wenzhuo4657
        des: 发送奖品
    */
    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);
}
