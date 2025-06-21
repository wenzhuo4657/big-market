package cn.wenzhuo4657.BigMarket.domain.user.model.entity;

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
public class UserInfoEntity {
//    目前假设的场景是生成用户，外部系统传来用户信息，内部进行创建用户
//    无论如何该系统内部一定要能够找到外部系统对应的用户，否则根本毫无意义

//    外部系统内的userid应该保证唯一，但是系统



    private String userId;

    private  String username;

    /**
     * 头像的url连接，
     */
//    todo 校验这个是一个头像连接
    private String  avatar_url;


    /**
     * 外部系统的标识，必须保证全局唯一，可以将其加入到配置库中
     */
    private String  external_system_id;



    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
//
        this.avatar_url = avatar_url;
    }
}
