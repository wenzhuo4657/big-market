package cn.wenzhuo4657.BigMarket.domain.award.service.distribute.impl;

import cn.wenzhuo4657.BigMarket.domain.award.model.aggregate.GiveOutPrizesAggregate;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.DistributeAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.UserAwardRecordEntity;
import cn.wenzhuo4657.BigMarket.domain.award.model.entity.UserCreditAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.award.model.valobj.AwardStateVO;
import cn.wenzhuo4657.BigMarket.domain.award.repository.IAwardRepository;
import cn.wenzhuo4657.BigMarket.domain.award.service.distribute.IDistributeAward;
import cn.wenzhuo4657.BigMarket.types.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/7
 * @description: 用户积分奖品
 */
@Component("user_credit_random")
public class UserCreditRandomAward implements IDistributeAward {
    @Resource
    private IAwardRepository repository;
    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) {
        Integer awardId = distributeAwardEntity.getAwardId();

//        这里允许传参中的奖品配置为空，为空时会去数据库中查询使用。
        String awardConfig = distributeAwardEntity.getAwardConfig();
        if (StringUtils.isBlank(awardConfig)){
            awardConfig=repository.queryAwardConfig(awardId);
        }


        String[]  creditRange=awardConfig.split(Constants.SPLIT);
        if (creditRange.length!=2){
            throw new RuntimeException("award_config 「" + awardConfig + "」配置不是一个范围值，如 1,100");
        }

        BigDecimal creditAmount = generateRandom(new BigDecimal(creditRange[0]), new BigDecimal(creditRange[1]));


        UserAwardRecordEntity userAwardRecordEntity= GiveOutPrizesAggregate.buildDistributeUserAwardRecordEntity(
                distributeAwardEntity.getUserId(),
                distributeAwardEntity.getOrderId(),
                awardId,
                AwardStateVO.complete
        );
        UserCreditAwardEntity userCreditAwardEntity = GiveOutPrizesAggregate.buildUserCreditAwardEntity(userAwardRecordEntity.getUserId(), creditAmount);
        GiveOutPrizesAggregate giveOutPrizesAggregate = new GiveOutPrizesAggregate();
        giveOutPrizesAggregate.setUserId(distributeAwardEntity.getUserId());
        giveOutPrizesAggregate.setUserAwardRecordEntity(userAwardRecordEntity);
        giveOutPrizesAggregate.setUserCreditAwardEntity(userCreditAwardEntity);


        repository.saveGiveOutPrizesAggregate(giveOutPrizesAggregate);
    }
// 返回精度3、min和max之间的随机数
    private BigDecimal generateRandom(BigDecimal min,BigDecimal max){
        if (min.equals(max))return  min;
        BigDecimal randomBigDecimal=min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.round(new MathContext(3));


    }
}
