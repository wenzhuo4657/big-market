package cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.chain;

/**
 * @className: AbstractLogicChain
 * @author: wenzhuo4657
 * @date: 2024/10/2
 * @Version: 1.0
 * @description:
 * 实现责任链装配ILogicChainArmory，也就是如何装配 ILogicChain接口的实现类
 */
public abstract class AbstractLogicChain implements ILogicChain {
    private  ILogicChain next;

    @Override
    public ILogicChain next() {
        return next;
    }
    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    protected abstract String ruleModel();
}
