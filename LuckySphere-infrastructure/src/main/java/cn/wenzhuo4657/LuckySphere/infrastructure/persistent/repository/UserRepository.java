package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.repository;

import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterUserInfoEntity;
import cn.wenzhuo4657.LuckySphere.domain.user.repository.IUserRepository;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.SystemDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.UserCreditAccountDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.System;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserCreditAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Repository
public class UserRepository  implements IUserRepository {


    @Autowired
    SystemDao systemDao;

    @Autowired
    UserCreditAccountDao userCreditAccountDao;


    @Override
    public String register(RegisterUserInfoEntity userInfoEntity) {
//        todo 这里的积分账户表并未完全利用RegisterUserInfoEntity 信息，待修改
        UserCreditAccount userCreditAccount = new UserCreditAccount();
        String userId = userInfoEntity.getExternal_system_userId()+"@"+userInfoEntity.getExternal_system_id();
        userCreditAccount.builder()
                .userId(userId)
                .accountStatus("open")
                .availableAmount(new BigDecimal(0))
                .totalAmount(new BigDecimal(0))
                .build();
        userCreditAccountDao.insert(userCreditAccount);
        return userId;
    }

    @Override
    public String registerSystem(String external_system_Id) {
        System system = new System();
        system.setId(UUID.randomUUID().toString());
        system.setSystemId(external_system_Id);
        systemDao.insertSystem(system);
        return system.getId();
    }
}
