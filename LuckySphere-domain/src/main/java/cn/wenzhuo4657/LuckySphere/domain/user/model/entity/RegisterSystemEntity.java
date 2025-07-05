package cn.wenzhuo4657.LuckySphere.domain.user.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterSystemEntity {


    private String external_system_Id;
}
