package cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.tree.impl;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.wenzhuo4657.LuckySphere.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: RuleLuckAwardLogicTreeNode
 * @author: wenzhuo4657
 * @date: 2024/10/4
 * @Version: 1.0
 * @description: 兜底奖品节点
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {


    @Resource
    private IStrategyRepository strategyRepository;
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-兜底奖品 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);
        String[] split = ruleValue.split(Constants.COLON);
        if (split.length==0){
            log.error("规则过滤-兜底奖品，兜底奖品未配置告警 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置 " + ruleValue);
        }
        Integer luckAwardId = Integer.valueOf(split[0]);
        String awardRuleValue=split.length>1?split[1]:"";
        // 写入延迟队列，延迟消费更新数据库记录。【在trigger的job；UpdateAwardStockJob 下消费队列，更新数据库记录】
        strategyRepository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                .strategyId(strategyId)
                .awardId(awardId)
                .build());
        /**
         *  @author:wenzhuo4657
            des:
         数据库中字段解释为：
             20,100 兜底奖品20，100以内随机积分
        */
        log.info("规则过滤-兜底奖品 userId:{} strategyId:{} awardId:{} awardRuleValue:{}", userId, strategyId, luckAwardId, awardRuleValue);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue)
                        .build())
                .build();
    }
}
