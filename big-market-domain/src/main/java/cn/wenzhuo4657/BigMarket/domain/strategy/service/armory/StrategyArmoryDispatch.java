package cn.wenzhuo4657.BigMarket.domain.strategy.service.armory;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @className: StrategyArmoryDispatch
 * @author: wenzhuo4657
 * @date: 2024/9/24
 * @Version: 1.0
 * @description:
 */
@Service
public class StrategyArmoryDispatch implements IStrategyDispatch,IStrategyArmory{
    private  Logger log= LoggerFactory.getLogger(StrategyArmoryDispatch.class);
    private IStrategyRepository strategyRepository;

    public StrategyArmoryDispatch(IStrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        try {

//            1,测率概率的装配
            List<StrategyAwardEntity> strategyAwardEntityList = strategyRepository.queryStrategyAwardList(strategyId);
            assembleLotteryStrategy(String.valueOf(strategyId),strategyAwardEntityList);



//          2，策略权重的装配，
            StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
            String ruleWeight = strategyEntity.getRuleWeight();
            if (StringUtils.isBlank(ruleWeight))return true;

            StrategyRuleEntity strategyRule=strategyRepository.queryStrategyRule(strategyId,ruleWeight);
            if (Objects.isNull(strategyRule))
                throw new  AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(),ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());


            Map<String, List<Integer>> ruleWeightValues = strategyRule.getRuleWeightValues();
            return true;
        } catch (Exception e) {
            log.info("cn.wenzhuo4657.BigMarket.domain.strategy.service.armory:assembleLotteryStrategy报错,{}",e);
            return  false;
        }
    }
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
        Collections.shuffle(list);
        Map<Integer,Integer> table=new LinkedHashMap<>(list.size());
        for (int i=0;i<list.size();i++){
            table.put(i,list.get(i));
        }

          //  wenzhuo TODO 2024/9/25 : 这里与之前版本有些不同，将key和StrategyId进行了区分，key取药搭配前缀获取redis中的值，而后者不许，但问题在于并未找到存后者键值对的操作。问题在于从输入数据的角度来看，这二者的值只是数据类型的不同。
        strategyRepository.storeStrategyAwardSearchRateTable(key,table.size(),table);

    }
    @Override
    public Integer getRandomAwardId(Long strategyId) {
        return null;
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        return null;
    }
}
