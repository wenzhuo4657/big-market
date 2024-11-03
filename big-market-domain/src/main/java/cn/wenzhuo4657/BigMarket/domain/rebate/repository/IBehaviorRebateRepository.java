package cn.wenzhuo4657.BigMarket.domain.rebate.repository;

import cn.wenzhuo4657.BigMarket.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.BehaviorTypeVO;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description: 行为返利仓储接口
 */
public interface IBehaviorRebateRepository {
    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);
}
