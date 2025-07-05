package cn.wenzhuo4657.LuckySphere.domain.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  该实体用于定义外部系统的用户信息，
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserInfoEntity {




    private String external_system_userId;

    private  String username;

    /**
     * 头像的url连接，
     */
//    todo 校验这个是一个头像连接
    private String  avatar_url;


    /**
     * 外部系统在本数据库中的id
     */
    private String  external_system_id;





    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {

        this.avatar_url = avatar_url;
    }
}
