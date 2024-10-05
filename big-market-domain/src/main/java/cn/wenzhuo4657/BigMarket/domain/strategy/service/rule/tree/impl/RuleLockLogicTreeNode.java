package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.impl;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @className: RuleLockLogicTreeNode
 * @author: wenzhuo4657
 * @date: 2024/10/4
 * @Version: 1.0
 * @description: 次数锁节点
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

      //  wenzhuo TODO 2024/10/5 : 暂时表示用户抽奖，实现后更改
    private Long userRaffleCount = 10L;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-次数锁 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        long RaffleCount=0L;
        try{
            RaffleCount=Long.parseLong(ruleValue);

        }catch (Exception e){
            throw new RuntimeException("规则过滤-次数锁异常：ruleValue "+ruleValue+"配置不正确");

        }
        if (userRaffleCount>=RaffleCount){
            return  DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .build();
        }
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
