package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.ILogicChain;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.ILogicChainArmory;
import lombok.*;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private final ApplicationContext  applicationContext;
    protected IStrategyRepository strategyRepository;

    /**
     *  @author:wenzhuo4657
        des: 责任链缓存
     注意：springbean工厂默认为单例模式，这一点是和责任链缓存想违背的，因为责任链节点的next属性的值是不一样的。
    所以此处对于每个责任链节点都加上了注解@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)表示原型模式，、
     即每次获取bean都返回一个新的示例。
    */
    private final  Map<Long,ILogicChain> strategyChainGroup;

    public DefaultChainFactory(ApplicationContext applicationContext, IStrategyRepository strategyRepository) {
        this.applicationContext = applicationContext;
        this.strategyRepository = strategyRepository;
        this.strategyChainGroup=new ConcurrentHashMap<>();
    }

    /**
     * @Author wenzhuo4657
     * @Param 策略id
     * @return 根据策略id装配责任链对象，且注意ILogicChain是单向链表，
     **/
    public ILogicChain openLogicChain(Long strategyId){
        ILogicChain chacheILogicChain = strategyChainGroup.get(strategyId);
        if (null!=chacheILogicChain)return  chacheILogicChain;

        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategyEntity.ruleModels();

        if (null== ruleModels||ruleModels.length==0){
            ILogicChain iLogicChain = applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class);
            strategyChainGroup.put(strategyId,iLogicChain);
            return iLogicChain;
        }

        ILogicChain iLogicChain = applicationContext.getBean(ruleModels[0], ILogicChain.class);
        ILogicChainArmory current=iLogicChain;
        for (int i=1;i< ruleModels.length;i++){
            ILogicChain iLogicChain1 = applicationContext.getBean(ruleModels[i], ILogicChain.class);
            current.appendNext(iLogicChain1);
        }
        current.appendNext(applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class));
        strategyChainGroup.put(strategyId,iLogicChain);
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
