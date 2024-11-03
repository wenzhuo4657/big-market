package cn.wenzhuo4657.BigMarket.domain.rebate.service;

import cn.wenzhuo4657.BigMarket.domain.rebate.event.SendRebateMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.BehaviorEntity;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import cn.wenzhuo4657.BigMarket.domain.rebate.repository.IBehaviorRebateRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/3
 * @description:
 */
@Service
public class BehaviorRebateService implements IBehaviorRebateService{


    @Resource
    private IBehaviorRebateRepository behaviorRebateRepository;
    @Resource
    private SendRebateMessageEvent sendRebateMessageEvent;
    @Override
    public List<String> createOrder(BehaviorEntity behaviorEntity) {
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS =
                behaviorRebateRepository.queryDailyBehaviorRebateConfig(behaviorEntity.getBehaviorTypeVO());
        if (null ==dailyBehaviorRebateVOS ||dailyBehaviorRebateVOS.isEmpty()) return null;

        List<String> orderIds=new ArrayList<>();
        List<BehaviorRebateAggregate> aggregates=new ArrayList<>();







        behaviorRebateRepository.saveUserRebateRecord(behaviorEntity.getUserId(),aggregates);
        return orderIds;
    }


}
