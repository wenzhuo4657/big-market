package cn.wenzhuo4657.BigMarket.domain.rebate.service;

import cn.wenzhuo4657.BigMarket.domain.rebate.event.SendRebateMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.BehaviorEntity;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.entity.TaskEntity;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import cn.wenzhuo4657.BigMarket.domain.rebate.model.valobj.TaskStateVO;
import cn.wenzhuo4657.BigMarket.domain.rebate.repository.IBehaviorRebateRepository;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
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
        /**
         *  @author:wenzhuo4657
            des:
         这里的返利类型更像是返利的子任务类型，该行为类型下，每一个返利类型都需要生成聚合订单，然后进行mq发送消息等正常消费流程。
        */
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS =
                behaviorRebateRepository.queryDailyBehaviorRebateConfig(behaviorEntity.getBehaviorTypeVO());
        if (null ==dailyBehaviorRebateVOS ||dailyBehaviorRebateVOS.isEmpty()) return null;

        List<String> orderIds=new ArrayList<>();
        List<BehaviorRebateAggregate> aggregates=new ArrayList<>();

        for(DailyBehaviorRebateVO vo:dailyBehaviorRebateVOS){
//            业务ID；用户ID_返利类型_外部透彻业务ID
            String bizid=behaviorEntity.getUserId()+ Constants.UNDERLINE+vo.getRebateType()+Constants.UNDERLINE+behaviorEntity.getOutBusinessNo();
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(behaviorEntity.getUserId())
//                    id无需保证分布式一致
                    .orderId(RandomStringUtils.randomNumeric(12))
                    .behaviorType(vo.getBehaviorType())
                    .rebateDesc(vo.getRebateDesc())
                    .rebateType(vo.getRebateType())
                    .rebateConfig(vo.getRebateConfig())
                    .outBusinessNo(behaviorEntity.getOutBusinessNo())
                    .bizId(bizid)
                    .build();


            orderIds.add(behaviorRebateOrderEntity.getOrderId());

            SendRebateMessageEvent.RebateMessage rebateMessage=SendRebateMessageEvent.RebateMessage.builder()
                    .userId(behaviorEntity.getUserId())
                    .rebateType(vo.getRebateType())
                    .rebateConfig(vo.getRebateConfig())
                    .bizId(bizid)
                    .build();
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> rebateMessageEventMessage=sendRebateMessageEvent.buildEventMessage(rebateMessage);


            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setUserId(behaviorEntity.getUserId());
            taskEntity.setTopic(sendRebateMessageEvent.topic());
            taskEntity.setMessageId(rebateMessageEventMessage.getId());
            taskEntity.setMessage(rebateMessageEventMessage);
            taskEntity.setState(TaskStateVO.create);

            BehaviorRebateAggregate rebateAggregate = BehaviorRebateAggregate.builder()
                    .userId(behaviorEntity.getUserId())
                    .behaviorRebateOrderEntity(behaviorRebateOrderEntity)
                    .taskEntity(taskEntity)
                    .build();
            aggregates.add(rebateAggregate);
        }
        behaviorRebateRepository.saveUserRebateRecord(behaviorEntity.getUserId(),aggregates);
  //  wenzhuo TODO 2024/12/9 :  订单id通过随机数生成，并不保证唯一，此处将其返回的作用？
        return orderIds;
    }

    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        return behaviorRebateRepository.queryOrderByOutBusinessNo(userId, outBusinessNo);
    }
}
