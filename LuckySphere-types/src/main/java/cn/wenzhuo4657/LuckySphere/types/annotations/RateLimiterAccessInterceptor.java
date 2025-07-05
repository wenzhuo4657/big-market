package cn.wenzhuo4657.LuckySphere.types.annotations;


import java.lang.annotation.*;


/**
 * @className: RateLimiterAccessInterceptor
 * @author: wenzhuo4657
 * @date: 12:53
 * @Version: 2.0
 * @description: 自定义拦截器注解（1，黑名单拦截，2，频率拦截）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RateLimiterAccessInterceptor {

    /**
     *  @author:wenzhuo4657
        des:
     all表示没有配置，该键实际上表示器限流器的key,用于区分不同请求。
    */
    String key() default "all";

    /**
     * 限制频次（每秒请求次数）
     */
    double permitsPerSecond();

    /**
     * 黑名单拦截（多少次限制permitsPerSecond后加入黑名单），0表示 不限制
     */
    double blacklistCount() default 0;

    /**
     * 拦截后的执行方法
     */
    String fallbackMethod();

}
