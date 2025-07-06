package cn.wenzhuo4657.LuckySphere.tigger.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserInfoDto  implements Serializable {
    private String external_system_userId;

    private  String username;

    /**
     * 头像的url连接，
     */

    private String  avatar_url;


    /**
     * 外部系统在本数据库中的id
     */
    private String  external_system_id;
}
