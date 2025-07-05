package cn.wenzhuo4657.LuckySphere.domain.strategy.service.armory;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.wenzhuo4657.LuckySphere.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @className: StrategyArmoryDispatch
 * @author: wenzhuo4657
 * @date: 2024/9/24
 * @Version: 1.0
 * @description:
 */
@Service("strategyArmoryDispatch")
public class StrategyArmoryDispatch extends AbstractStrategyAlgorithm {
    private  Logger log= LoggerFactory.getLogger(StrategyArmoryDispatch.class);


    @Autowired
    public StrategyArmoryDispatch(IStrategyRepository strategyRepository) {
        super(strategyRepository);
    }

    /**
     * @Author wenzhuo4657
     * @Param
     * key:装配redis中的key，
     * 此时分为了两种key，第一种权限概率装配没有改变，和第二种权重装配互不影响，因为两者的key不同。
     * strategyAwardEntityList：装配redis的奖品实体列表
     * des：概率表装配，
     **/
    @Deprecated
    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntityList){

        BigDecimal min=strategyAwardEntityList.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal totalDecimal = strategyAwardEntityList.stream()
                .map(entity -> {
                    return entity.getAwardRate();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal RateRange= totalDecimal.divide(min, 0, RoundingMode.CEILING);

        List<Integer> list=new ArrayList<>(RateRange.intValue());
        for (StrategyAwardEntity entity:strategyAwardEntityList){
            Integer awardId = entity.getAwardId();
            BigDecimal awardRate = entity.getAwardRate();
            for (int i=0;i<RateRange.multiply(awardRate).setScale(0,RoundingMode.CEILING).intValue();i++){
                list.add(awardId);
            }
        }
        Collections.shuffle(list);//随机乱序
        Map<Integer,Integer> table=new LinkedHashMap<>(list.size());
        for (int i=0;i<list.size();i++){
            table.put(i,list.get(i));
        }
        strategyRepository.storeStrategyAwardSearchRateTable(key,table.size(),table);

    }
    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = strategyRepository.getRateRange(strategyId);
        return strategyRepository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        int rateRange = strategyRepository.getRateRange(key);
        return strategyRepository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }


    @Override
    public Integer getRandomAwardId(String key) {
        int rateRange = strategyRepository.getRateRange(key);
        return strategyRepository.getStrategyAwardAssemble(key,secureRandom.nextInt(rateRange));
    }

    @Override
    public Boolean subtractionAwardStock(Long strategyId, Integer awardId) {
        String cacaheKey=Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY+strategyId+Constants.UNDERLINE+awardId;
        return strategyRepository.subtractionAwardStock(cacaheKey);
    }

    /**
     *  @author:wenzhuo4657
        des: 初始化策略奖品库存到redis中
    */
    public  void cacheStrategyAwardCount(Long strategyId, Integer awardId, Integer awardCount){
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
        strategyRepository.cacheStrategyAwardCount(cacheKey,awardCount);
    }

    @Override
    protected void armoryAlgorithm(String key, List<StrategyAwardEntity> strategyAwardEntityList) {
        // 1. 概率最小值
        BigDecimal minAwardRate = minAwardRate(strategyAwardEntityList);
        // 2. 概率范围值
        BigDecimal rateRange = convert(minAwardRate.doubleValue());
        // 3. 装配策略分布表
        List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAward : strategyAwardEntityList) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();
            // 计算出每个概率值需要存放到查找表的数量，循环填充
            for (int i = 0; i < rateRange.multiply(awardRate).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }
        /**
         *  @author:wenzhuo4657
            des:
         1，提高精度使策略分布范围增大，
         2，随机乱序处理
        随机的实质通过这两点实现，但更为重要的是后者，后者让策略分布范围增大有了实际的意义。
        */
        Collections.shuffle(strategyAwardSearchRateTables);//乱序处理

        // 3. 生成出Map集合，key值，对应的就是后续的概率值。通过概率来获得对应的奖品ID
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTable = new LinkedHashMap<>(strategyAwardSearchRateTables.size());
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTables.get(i));
        }
        strategyRepository.storeStrategyAwardSearchRateTable(key, shuffleStrategyAwardSearchRateTable.size(), shuffleStrategyAwardSearchRateTable);

    }
    /**
     *  @author:wenzhuo4657
        des:  返回中奖概率最小值
    */
    protected BigDecimal minAwardRate(List<StrategyAwardEntity> strategyAwardEntities){
        return strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    protected BigDecimal convert(double min) {
        if (0 == min) return new BigDecimal("1");

        String minStr = String.valueOf(min);

        // 小数点前
        String beginVale = minStr.substring(0, minStr.indexOf("."));
        int beginLength = 0;
        if (Double.parseDouble(beginVale) > 0) {
            beginLength = minStr.substring(0, minStr.indexOf(".")).length();
        }

        // 小数点后
        String endValue = minStr.substring(minStr.indexOf(".") + 1);
        int endLength = 0;
        if (Double.parseDouble(endValue) > 0) {
            endLength = minStr.substring(minStr.indexOf(".") + 1).length();
        }

        double pow = Math.pow(10, beginLength + endLength);
//        避免精度损失之类的错误，使用new BigDecimal(String val)构造器
        return new BigDecimal(String.valueOf(pow));
    }

    @Override
    public boolean assembleLotteryStrategyByActivityId(Long activityId) {
        Long strategyId = strategyRepository.queryStrategyIdByActivityId(activityId);
        return assembleLotteryStrategy(strategyId);
    }
}
