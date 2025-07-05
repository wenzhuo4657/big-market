package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system")
public class System {
    private String id;
    private  String systemId;
}
