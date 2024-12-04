package cn.wenzhuo4657.BigMarket.domain.auth.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/4
 * @description:
 */
@Slf4j
@Service
public class AuthService extends  AbstractAuthService{
    @Override
    public boolean checkToken(String token) {
        return isVerify(token);
    }

    @Override
    public String openid(String token) {
        Claims claims = decode(token);
        return claims.get("openId").toString();
    }
}
