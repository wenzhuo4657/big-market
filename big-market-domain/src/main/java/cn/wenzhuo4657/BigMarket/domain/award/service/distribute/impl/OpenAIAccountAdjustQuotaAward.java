package cn.wenzhuo4657.BigMarket.domain.award.service.distribute.impl;

import cn.wenzhuo4657.BigMarket.domain.award.adapter.port.IAwardPort;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.DistributeAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.award.repository.IAwardRepository;
import cn.wenzhuo4657.BigMarket.domain.award.service.distribute.IDistributeAward;
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
        Integer awardId = distributeAwardEntity.getAwardId();
        String awardConfig = distributeAwardEntity.getAwardConfig();
        if (StringUtils.isBlank(awardConfig)) {
            awardConfig = repository.queryAwardConfig(awardId);
        }
  //  wenzhuo TODO 2024/12/3 : 注意，对于openAi奖品，awardConfig只能配置为数字字符串，  "Integer.valueOf(awardConfig)"
        port.adjustAmount(distributeAwardEntity.getUserId(), Integer.valueOf(awardConfig));
    }
}
