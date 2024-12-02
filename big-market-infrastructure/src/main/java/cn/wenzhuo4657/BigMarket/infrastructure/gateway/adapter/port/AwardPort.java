package cn.wenzhuo4657.BigMarket.infrastructure.gateway.adapter.port;

import cn.wenzhuo4657.BigMarket.domain.award.adapter.port.IAwardPort;
import cn.wenzhuo4657.BigMarket.infrastructure.gateway.IOpenAIAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description:
 */
@Slf4j
@Service
public class AwardPort implements IAwardPort {

    @Value("${gateway.config.big-market-appId}")
    private String BIG_MARKET_APPID;
    @Value("${gateway.config.big-market-appToken}")
    private String BIG_MARKET_APPTOKEN;

    @Resource
    private IOpenAIAccountService openAIAccountService;
    @Override
    public void adjustAmount(String userId, Integer increaseQuota) throws Exception {

    }
}
