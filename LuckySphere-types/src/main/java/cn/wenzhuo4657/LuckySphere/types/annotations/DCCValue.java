package cn.wenzhuo4657.LuckySphere.types.annotations;

import java.lang.annotation.*;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/19
 * @description: 动态配置注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DCCValue {

    /**
     *  @author:wenzhuo4657
        des:
     格式： name:value(name表示同步到zookeeper的节点名称，value表示实际配置的值。)
     目前该配置表示是否启动RateLimiterAccessInterceptor注解，格式为 rateLimiterSwitch:close,默认为空表示不启用
    */
    String value() default "";

}
