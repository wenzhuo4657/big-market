package cn.wenzhuo4657.LuckySphere.domain.strategy.service.armory;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/10
 * @description: 模板流程定义
 */
public abstract class AbstractStrategyAlgorithm implements IStrategyDispatch,IStrategyArmory {


    protected IStrategyRepository strategyRepository;
    protected final SecureRandom secureRandom=new SecureRandom();

    public AbstractStrategyAlgorithm(IStrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public boolean assembleLotteryStrategyByActivityId(Long activityId) {
        Long strategyId = strategyRepository.queryStrategyIdByActivityId(activityId);
        return assembleLotteryStrategy(strategyId);
    }

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
//        1,获取策略奖品实体列表
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);
//        2，缓存奖品库存
        for (StrategyAwardEntity strategyAward:strategyAwardEntities){
            cacheStrategyAwardCount(strategyId,strategyAward.getAwardId(),strategyAward.getAwardCountSurplus());
        }
//          3,策略概率表装配
        armoryAlgorithm(String.valueOf(strategyId),strategyAwardEntities);

//          4,策略权重值装配
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight=strategyEntity.getRuleWeight();
        if (StringUtils.isBlank(ruleWeight)){
            return true;  //截断处理，不存在权重规则
        }

        StrategyRuleEntity strategyRuleEntity = strategyRepository.queryStrategyRule(strategyId, ruleWeight);

        if (Objects.isNull(strategyRuleEntity)){
            throw new AppException( ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(),ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValues = strategyRuleEntity.getRuleWeightValues();
        for (String key:ruleWeightValues.keySet()){
            List<Integer> integers = ruleWeightValues.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> !integers.contains(entity.getAwardId()));
            armoryAlgorithm(String.valueOf(strategyId).concat(Constants.UNDERLINE).concat(key), strategyAwardEntitiesClone);

        }
        return true;
    }

    /**
     *  @author:wenzhuo4657
        des: 缓存策略奖品库存
    */
    public abstract void cacheStrategyAwardCount(Long strategyId, Integer awardId, Integer awardCount);
    /**
     *  @author:wenzhuo4657
        des: 缓存策略概率分布表
     ps:
    无论是默认装配，又或者是权重装配，其实质上都是<key,奖品>的装配便于实现随机抽奖的策略，二者最终都生成一张随机散列的表，
    请不要产生误解，将二者区分。
    */
    protected abstract void armoryAlgorithm(String key, List<StrategyAwardEntity> strategyAwardEntityList);

}
