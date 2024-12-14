package cn.wenzhuo4657.BigMarket.domain.award.repository;

import cn.wenzhuo4657.BigMarket.domain.award.model.aggregate.GiveOutPrizesAggregate;
import cn.wenzhuo4657.BigMarket.domain.award.model.aggregate.UserAwardRecordAggregate;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
 */
public interface IAwardRepository {
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

    String queryAwardConfig(Integer awardId);

    /**
     *  @author:wenzhuo4657
        des:
     积分奖品订单更新（1，更新订单状态，2，账户发奖）
    */
    void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate);

    /**
     *  @author:wenzhuo4657
        des:
     非积分奖品订单更新（仅更新订单状态）
    */
    void saveGiveOutPrizesAggregate(UserAwardRecordEntity userAwardRecord);

    String queryAwardKey(Integer awardId);
}
