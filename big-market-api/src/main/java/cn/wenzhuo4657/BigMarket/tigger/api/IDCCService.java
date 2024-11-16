package cn.wenzhuo4657.BigMarket.tigger.api;

import cn.wenzhuo4657.BigMarket.tigger.api.reponse.Response;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/16
 * @description: DCC（动态配置中心）
 */
public interface IDCCService {
    Response<Boolean> updateConfig(String key, String value);
}
