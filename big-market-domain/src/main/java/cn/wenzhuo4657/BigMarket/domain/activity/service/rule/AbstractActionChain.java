package cn.wenzhuo4657.BigMarket.domain.activity.service.rule;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
public abstract class AbstractActionChain implements  IActionChain{
    private IActionChain next;
    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}
