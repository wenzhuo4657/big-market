package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RuleActionEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RaffleLogicType;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.annotation.LogicStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.ILogicFilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: DefaultLogicFactory
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description: 默认逻辑规则工厂
 */
@Service
public class DefaultLogicFactory {
    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();



    public <T extends RuleActionEntity.RaffleEntity>    Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }


    /**
     *  @author:wenzhuo4657
        des: 装配logicFilterMap，通过字节码文件检查是否存在对应的注解。如果存在则加入.
    */
    @Autowired
    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters) {
        logicFilters.forEach(
                logic->{
                    LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
                    if (null != strategy) {
                        this.logicFilterMap.put(strategy.logicMode().getCode(), logic);
                    }
                }
        );
    }


    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_WIGHT("rule_weight","【抽奖前规则】根据抽奖权重返回可抽奖范围KEY", RaffleLogicType.before),
        RULE_BLACKLIST("rule_blacklist","【抽奖前规则】黑名单规则过滤，命中黑名单则直接返回",RaffleLogicType.before),
        RULE_LOCK("rule_lock","【抽奖中规则】抽奖n次后可解锁某些奖品",RaffleLogicType.center),
        RULE_LUCK_AWARD("rule_luck_award","【抽奖后规则】抽奖n次后，对应奖品可解锁抽奖",RaffleLogicType.center)

        ;
        private final String code;
        private final String info;
        private final  String type;

        public  static  boolean isCenter(String code){
            return RaffleLogicType.center.equals(LogicModel.valueOf(code.toUpperCase()).type);
        }

        public  static  boolean isAfter(String code){
            return RaffleLogicType.after.equals(LogicModel.valueOf(code.toUpperCase()).type);
        }




    }

}
