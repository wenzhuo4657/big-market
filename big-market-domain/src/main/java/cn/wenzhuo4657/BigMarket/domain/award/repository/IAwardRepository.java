package cn.wenzhuo4657.BigMarket.domain.award.repository;

import cn.wenzhuo4657.BigMarket.domain.award.model.aggregate.GiveOutPrizesAggregate;
import cn.wenzhuo4657.BigMarket.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
 */
public interface IAwardRepository {
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

    String queryAwardConfig(Integer awardId);

    void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate);

    String queryAwardKey(Integer awardId);
}
