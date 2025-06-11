package cn.wenzhuo4657.BigMarket.types.utils;


import javafx.scene.input.DataFormat;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;

/**
 *  该工具类用于生成各种随机的的订单id
 */
public class RandomOrderIdUtils {


    static  final  int Default_length=12;

    static  int length=Default_length;


    /**
     * 根据时间戳实现的订单id，程序内唯一
     * ps：该id可以和分布式id结合形成分布式场景下的唯一id
     */

    public static String getOrderIdByTime(){
        long l = System.currentTimeMillis();
        SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(l);
        return format;
    }

}
