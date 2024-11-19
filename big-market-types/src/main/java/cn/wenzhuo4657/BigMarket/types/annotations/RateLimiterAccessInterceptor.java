package cn.wenzhuo4657.BigMarket.types.annotations;


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
     * 用哪个字段作为拦截标识，未配置则默认走全部,
     * 可配置字段实际上为：
     * all、permitsPerSecond和blacklistCount，其中all和
     * 目前aop实现，blacklistCount依赖于限流permitsPerSecond，
     * permitsPerSecond记录超频次数，blacklistCount根据超频次数将其加入黑名单
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
