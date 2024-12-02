package cn.wenzhuo4657.BigMarket.domain.award.adapter.port;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description: 奖品对接接口
 */
public interface IAwardPort {

    void adjustAmount(String userId, Integer increaseQuota) throws Exception;
}
