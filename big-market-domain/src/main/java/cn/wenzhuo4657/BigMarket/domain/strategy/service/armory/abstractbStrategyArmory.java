package cn.wenzhuo4657.BigMarket.domain.strategy.service.armory;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @className: StrategyArmory
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:45
 * @Version: 1.0
 * @description:
 */
@Service
public abstract class abstractbStrategyArmory implements  IStrategyArmory,IStrategyDispatch{

    private Logger log= LoggerFactory.getLogger(abstractbStrategyArmory.class);

    private IStrategyRepository strategyRepository;

    public abstractStrategyArmory(IStrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        try {
            List<StrategyAwardEntity> strategyAwardEntityList = strategyRepository.queryStrategyAwardList(strategyId);

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

            strategyRepository.storeStrategyAwardSearchRateTable(strategyId,table.size(),table);


            return true;
        } catch (Exception e) {
            log.info("cn.wenzhuo4657.BigMarket.domain.strategy.service.armory:assembleLotteryStrategy报错,{}",e);
           return  false;
        }

    }



}