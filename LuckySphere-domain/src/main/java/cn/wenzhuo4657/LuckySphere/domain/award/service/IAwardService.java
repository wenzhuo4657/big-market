package cn.wenzhuo4657.LuckySphere.domain.award.service;

import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.DistributeAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description: 奖品符接口
 */
public interface IAwardService {
    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

    /**
     * 配送发货奖品
     */
    void distributeAward(DistributeAwardEntity distributeAwardEntity) throws Exception;
}
