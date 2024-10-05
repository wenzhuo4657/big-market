package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.ILogicChain;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.ILogicChainArmory;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @className: DefaultChainFactory
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description:
 * 默认责任链工厂
 */
@Service
public class DefaultChainFactory {
    private  final Map<String, ILogicChain> logicChainMap;
    protected IStrategyRepository strategyRepository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainMap, IStrategyRepository strategyRepository) {
        this.logicChainMap = logicChainMap;
        this.strategyRepository = strategyRepository;
    }


    /**
     * @Author wenzhuo4657
     * @Param 策略id
     * @return 根据策略id装配责任链对象，且注意ILogicChain是单向链表，
     **/
    public ILogicChain openLogicChain(Long strategyId){
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategyEntity.ruleModels();
        if (null== ruleModels||ruleModels.length==0){
            return logicChainMap.get(LogicModel.RULE_DEFAULT.code);
        }
        ILogicChain iLogicChain = logicChainMap.get(ruleModels[0]);
        ILogicChainArmory current=iLogicChain;
        for (int i=1;i< ruleModels.length;i++){
            ILogicChain iLogicChain1 = logicChainMap.get(ruleModels[i]);
            current.appendNext(iLogicChain1);
        }
        current.appendNext(logicChainMap.get(LogicModel.RULE_DEFAULT.code));
        return iLogicChain;
    }




    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }
}
