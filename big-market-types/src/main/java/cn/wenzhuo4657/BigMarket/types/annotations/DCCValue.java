package cn.wenzhuo4657.BigMarket.types.annotations;

import java.lang.annotation.*;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/19
 * @description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DCCValue {

    String value() default "";

}
