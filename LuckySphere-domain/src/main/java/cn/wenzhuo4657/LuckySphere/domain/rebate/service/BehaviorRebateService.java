package cn.wenzhuo4657.LuckySphere.domain.rebate.service;

import cn.wenzhuo4657.LuckySphere.domain.rebate.event.SendRebateMessageEvent;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.TaskEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj.TaskStateVO;
import cn.wenzhuo4657.LuckySphere.domain.rebate.repository.IBehaviorRebateRepository;
import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.event.BaseEvent;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import cn.wenzhuo4657.LuckySphere.types.utils.RandomOrderIdUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.units.qual.A;
import org.omg.CORBA.PRIVATE_MEMBER;
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
         * 0,是否存在对应用户
         */
        if (null == behaviorEntity.getUserId()||!behaviorRebateRepository.queryUserInfoByUserId(behaviorEntity.getUserId()))
            throw  new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NOT_EXIST.getCode(),ResponseCode.USER_CREDIT_ACCOUNT_NOT_EXIST.getInfo());
        /**
         *  1，空配置查询
        */
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS =
                behaviorRebateRepository.queryDailyBehaviorRebateConfig(behaviorEntity.getBehaviorTypeVO());
        if (null ==dailyBehaviorRebateVOS ||dailyBehaviorRebateVOS.isEmpty()) return null;




        /**
         *  2，是否已签到查询
        */
        List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = queryOrderByOutBusinessNo(behaviorEntity.getUserId(), behaviorEntity.getOutBusinessNo());
        if (behaviorRebateOrderEntities.size()!=0){
            return  null;
        }


        List<String> orderIds=new ArrayList<>();
        List<BehaviorRebateAggregate> aggregates=new ArrayList<>();

        for(DailyBehaviorRebateVO vo:dailyBehaviorRebateVOS){
//            业务ID；用户ID_返利类型_外部透彻业务ID
            String bizid=behaviorEntity.getUserId()+ Constants.UNDERLINE+vo.getRebateType()+Constants.UNDERLINE+behaviorEntity.getOutBusinessNo();
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(behaviorEntity.getUserId())
                    .orderId(RandomOrderIdUtils.getOrderId())
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
        return orderIds;
    }

    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        return behaviorRebateRepository.queryOrderByOutBusinessNo(userId, outBusinessNo);
    }
}
