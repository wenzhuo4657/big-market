package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.tree;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain.ILogicChain;

/**
 * @className: ILogicChainArmory
 * @author: wenzhuo4657
 * @date: 2024/10/4
 * @Version: 1.0
 * @description: 规则书装配接口定义
 */
public interface ILogicChainArmory {

    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);
}
