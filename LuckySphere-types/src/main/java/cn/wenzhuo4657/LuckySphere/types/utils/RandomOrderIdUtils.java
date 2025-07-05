package cn.wenzhuo4657.LuckySphere.types.utils;


import javafx.scene.input.DataFormat;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 *  该工具类用于生成各种随机的的订单id
 */
public class RandomOrderIdUtils {


    static  final  int Default_length=12;

    static  int length=Default_length;


    /**
     * 全局统一调用方法，内部调用本工具类的其他方法，便于修改实现。
     */

    public  static String getOrderId(){
        return  getOrderIdByUUID();
    }


    /**
     * 根据时间戳实现的订单id，程序内唯一
     * ps：该id可以和分布式id结合形成分布式场景下的唯一id
     */

    public    static String getOrderIdByTime(){
//        todo  Duplicate entry '250620161712' for key 'user_raffle_order_000.uq_order_id'
//        解决方案：
//        1，处理库表的唯一索引失效问题（shareding-jdbc并不会处理分表下的唯一索引）。
//        2，借助分布式id完成唯一索引。
//        3，使用UUID生成唯一索引

//        然而是高并发的情况下UUID也会产生唯一索引冲突，这还是在sharding-jdbc索引失效的场景下，
//        可以尝试换个思路，我们不去让索引全局生效，而是单表索引有效！
//        利用分片策略让一个用户只能走到一个分片，而orderid的唯一索引可以保证单表唯一，
//        这样他的userid+orderid就可以保证全局唯一
//        问题： 某些表可能会非常臃肿，因此在路由用户的id或者说生成用户id是需要合理分配

//        todo 设计用户维度，管理用户的创建、生成
        long l = System.currentTimeMillis();
        SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String format = simpleDateFormat.format(l);
        return format;
    }


    /**
     * 使用UUID生成订单id
     */
    public static  String getOrderIdByUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static void main(String[] args) {
        System.out.println(getOrderIdByUUID());
    }

}
