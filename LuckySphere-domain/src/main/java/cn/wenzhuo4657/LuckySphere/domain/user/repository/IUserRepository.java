package cn.wenzhuo4657.LuckySphere.domain.user.repository;


import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterUserInfoEntity;

public interface IUserRepository {

    String register(RegisterUserInfoEntity userInfoEntity);


    String registerSystem(String external_system_Id);
}
