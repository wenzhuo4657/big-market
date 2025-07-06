package cn.wenzhuo4657.LuckySphere.domain.user.service;

import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterSystemEntity;
import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterUserInfoEntity;
import cn.wenzhuo4657.LuckySphere.domain.user.repository.IUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements  IUserInfoService{


    @Autowired
    private IUserRepository userRepository;
    @Override
    public String registerUser(RegisterUserInfoEntity userInfoEntity) {
        return userRepository.register(userInfoEntity);
    }

    @Override
    public String register(RegisterSystemEntity registerSystemEntity) {
        if (registerSystemEntity != null&& !StringUtils.isBlank(registerSystemEntity.getExternal_system_Id())){
            return userRepository.registerSystem(registerSystemEntity.getExternal_system_Id());
        }
        return "";
    }
}
