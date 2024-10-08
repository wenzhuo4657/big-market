package cn.wenzhuo4657.BigMarket.domain.strategy.service.raffle;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleTreeVo;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.AbstractRaffleStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyDispatch;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.ILogicChain;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @className: DefaultRaffleStrategy
 * @author: wenzhuo4657
 * @date: 2024/9/28
 * @Version: 1.0
 * @description:
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {


    public DefaultRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(strategyRepository, strategyDispatch, defaultChainFactory, defaultTreeFactory);

    }

    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain iLogicChain = defaultChainFactory.openLogicChain(strategyId);
        return iLogicChain.logic(userId,strategyId);
    }

    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = strategyRepository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        /**
         *  @author:wenzhuo4657
            des:
            注意该截断，此时数据库中规则模型中已经没有责任链相关规则字段，所以如果不存在规则等价于一定没有需要执行的规则树
        */
        if (null==strategyAwardRuleModelVO|| StringUtils.isBlank(strategyAwardRuleModelVO.getRuleModels())){
            return DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).build();
        }
        /**
         *  @author:wenzhuo4657
            des:规则树设计的初衷，或者说为何不在责任链中，就是其规则之间并不互斥，并非前者执行完毕，后者就不能参与，这也就以为这其treeId并非某条规则，
         因此此处将全部规则字段作为key进行查询。
         ps：此处仅仅实现规则次数锁，从效果上来看是和责任链没有区别，但在日后进行规则填充时，就可以产生区别了。
        */
        RuleTreeVo ruleTreeVo = strategyRepository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (Objects.isNull(ruleTreeVo)){
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModelVO.getRuleModels());
        }
        IDecisionTreeEngine iDecisionTreeEngine = defaultTreeFactory.openLogicTree(ruleTreeVo);
        return iDecisionTreeEngine.process(userId, strategyId, awardId);
    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException {
        return strategyRepository.takeQueueValue();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        strategyRepository.updateStrategyAwardStock(strategyId,awardId);

    }
}
