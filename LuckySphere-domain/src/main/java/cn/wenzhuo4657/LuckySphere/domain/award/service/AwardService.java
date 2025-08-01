package cn.wenzhuo4657.LuckySphere.domain.award.service;

import cn.wenzhuo4657.LuckySphere.domain.award.event.SendAwardMessageEvent;
import cn.wenzhuo4657.LuckySphere.domain.award.model.aggregate.UserAwardRecordAggregate;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.DistributeAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserAwardRecordEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.valobj.TaskStateVO;
import cn.wenzhuo4657.LuckySphere.domain.award.repository.IAwardRepository;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.TaskEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.service.distribute.IDistributeAward;
import cn.wenzhuo4657.LuckySphere.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
 */
@Slf4j
@Service
public class AwardService implements IAwardService{
    @Resource
    private IAwardRepository awardRepository;
    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;

    @Resource
    private Map<String, IDistributeAward> distributeAwardMap;
    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessage.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessage.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessage.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        sendAwardMessage.setOrderId(userAwardRecordEntity.getOrderId());
        sendAwardMessage.setAwardConfig(userAwardRecordEntity.getAwardConfig());
        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage = sendAwardMessageEvent.buildEventMessage(sendAwardMessage);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userAwardRecordEntity.getUserId());
        taskEntity.setTopic(sendAwardMessageEvent.topic());
        taskEntity.setMessageId(sendAwardMessageEventMessage.getId());
        taskEntity.setMessage(sendAwardMessageEventMessage);
        taskEntity.setState(TaskStateVO.create);


        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .taskEntity(taskEntity)
                .userAwardRecordEntity(userAwardRecordEntity)
                .build();
        awardRepository.saveUserAwardRecord(userAwardRecordAggregate);
    }

    @Override
    public void distributeAward(DistributeAwardEntity distributeAwardEntity) throws Exception {
        String awardKey=awardRepository.queryAwardKey(distributeAwardEntity.getAwardId());
        if (null==awardKey){
            log.error("分发奖品，奖品ID不存在。awardKey:{}", awardKey);
            return;
        }
        /**
         *  @author:wenzhuo4657
            des:根据awardKey选择奖品分发策略，
        */
        IDistributeAward distributeAward = distributeAwardMap.get(awardKey);
        if (null==distributeAward){
            log.error("分发奖品，对应的服务不存在。awardKey:{}", awardKey);
            throw new RuntimeException("分发奖品，奖品" + awardKey + "对应的服务不存在");
        }
        distributeAward.giveOutPrizes(distributeAwardEntity);

    }
}
