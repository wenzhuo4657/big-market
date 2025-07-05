package cn.wenzhuo4657.LuckySphere.domain.user.service;

import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterSystemEntity;
import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterUserInfoEntity;
import org.springframework.stereotype.Service;


public interface IUserInfoService {


    /**
     *  外部系统注册用户
     * @return  本系统转换的用户id
     */
    String registerUser(RegisterUserInfoEntity userInfoEntity);


    /**
     * 外部系统注册
     */
    String register(RegisterSystemEntity registerSystemEntity);




}
