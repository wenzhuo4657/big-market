package cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.chain;

/**
 * @className: ILogicChainArmory
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description: 责任链装配定义，如果装配ILogicChain的实现类。
 */
public interface ILogicChainArmory {
    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);
}
