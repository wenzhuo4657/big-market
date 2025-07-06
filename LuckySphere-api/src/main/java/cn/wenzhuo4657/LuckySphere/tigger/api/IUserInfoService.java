package cn.wenzhuo4657.LuckySphere.tigger.api;


import cn.wenzhuo4657.LuckySphere.tigger.api.dto.RegisterUserInfoDto;
import cn.wenzhuo4657.LuckySphere.tigger.api.reponse.Response;

import java.util.List;

/**
 * 用户同步接口
 * todo 这里后续需要支持mq同步，参考认证中心的用户同步策略
 */
public interface IUserInfoService {


    /**
     * 注册用户
     */
    Response<String> registerUser(RegisterUserInfoDto registerUserInfoDto);


    /**
     * 注册外部系统信息
     */
    Response<String> registerExternalSystem(String externalSystemId);


    /**
     * 批量同步用户信息，实际上就是批量注册用户，信息传list即可
     */
    Response<String> batchRegisterUser(List<RegisterUserInfoDto> list);


}
