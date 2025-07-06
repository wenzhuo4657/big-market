package cn.wenzhuo4657.LuckySphere.trigger.http;


import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterSystemEntity;
import cn.wenzhuo4657.LuckySphere.domain.user.model.entity.RegisterUserInfoEntity;
import cn.wenzhuo4657.LuckySphere.domain.user.service.UserInfoService;
import cn.wenzhuo4657.LuckySphere.tigger.api.IUserInfoService;
import cn.wenzhuo4657.LuckySphere.tigger.api.dto.RegisterUserInfoDto;
import cn.wenzhuo4657.LuckySphere.tigger.api.reponse.Response;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/${app.config.api-version}/raffle/user/")
@DubboService(version = "1.0")
public class UserInfoController  implements IUserInfoService {


    @Resource
    private UserInfoService userInfoService;

    @Override
    @PostMapping("registerUserInfo")
    public Response<String> registerUser(@RequestBody RegisterUserInfoDto registerUserInfoDto) {

        RegisterUserInfoEntity build = RegisterUserInfoEntity.builder()
                .external_system_id(registerUserInfoDto.getExternal_system_id())
                .external_system_userId(registerUserInfoDto.getExternal_system_userId())
                .username(registerUserInfoDto.getUsername())
                .avatar_url(registerUserInfoDto.getAvatar_url())
                .build();

        String userId = userInfoService.registerUser(build);
        return  Response.<String>builder()
                .info(ResponseCode.SUCCESS.getInfo())
                .code(ResponseCode.SUCCESS.getCode())
                .data(userId).build();

    }

    @Override
    public Response<String> batchRegisterUser(List<RegisterUserInfoDto> list) {
        for (RegisterUserInfoDto registerUserInfoDto : list) {
            Response<String> stringResponse = registerUser(registerUserInfoDto);
        }
//        todo 这个接口待修正
        return null;
    }



    @Override
    @PostMapping("registerExternalSystem")
    public Response<String> registerExternalSystem(@RequestParam("external_system_Id") String external_system_Id) {
        RegisterSystemEntity registerSystemEntity = new RegisterSystemEntity(external_system_Id);
        String register = userInfoService.register(registerSystemEntity);
        return  Response.<String>builder()
                .info(ResponseCode.SUCCESS.getInfo())
                .code(ResponseCode.SUCCESS.getCode())
                .data(register).build();

    }

}
