package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("externalSystem")
public class System {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String systemId;
}
