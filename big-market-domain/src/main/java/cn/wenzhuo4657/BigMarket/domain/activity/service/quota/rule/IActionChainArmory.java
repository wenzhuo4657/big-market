package cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:  活动过滤链装配接口定义
 */
public interface IActionChainArmory {
    IActionChain next();

    IActionChain appendNext(IActionChain next);
}
