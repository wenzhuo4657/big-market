package cn.wenzhuo4657.BigMarket.domain.auth.service;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/4
 * @description: token权限认证
 */
public interface IAuthService {

    boolean checkToken(String token);

    String openid(String token);
}
