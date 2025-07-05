package cn.wenzhuo4657.LuckySphere.domain.award.service.distribute.impl;

import cn.wenzhuo4657.LuckySphere.domain.award.adapter.port.IAwardPort;
import cn.wenzhuo4657.LuckySphere.domain.award.model.aggregate.GiveOutPrizesAggregate;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.DistributeAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserAwardRecordEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserCreditAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.valobj.AwardStateVO;
import cn.wenzhuo4657.LuckySphere.domain.award.repository.IAwardRepository;
import cn.wenzhuo4657.LuckySphere.domain.award.service.distribute.IDistributeAward;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/3
 * @description: OpenAI 账户调额奖品
 */
@Component("openai_use_count")
public class OpenAIAccountAdjustQuotaAward implements IDistributeAward {

    @Resource
    private IAwardPort port;
    @Resource
    private IAwardRepository repository;

    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) throws Exception {
//        todo 该服务无效，暂时关闭
//        Integer awardId = distributeAwardEntity.getAwardId();
//        String awardConfig = distributeAwardEntity.getAwardConfig();
//        if (StringUtils.isBlank(awardConfig)) {
//            awardConfig = repository.queryAwardConfig(awardId);
//        }
//        port.adjustAmount(distributeAwardEntity.getUserId(), Integer.valueOf(awardConfig));
//        UserAwardRecordEntity userAwardRecordEntity= GiveOutPrizesAggregate.buildDistributeUserAwardRecordEntity(
//                distributeAwardEntity.getUserId(),
//                distributeAwardEntity.getOrderId(),
//                awardId,
//                AwardStateVO.complete
//        );
//
//        repository.saveGiveOutPrizesAggregate(userAwardRecordEntity);
    }
}
