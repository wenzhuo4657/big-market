package cn.wenzhuo4657.BigMarket.aop;

import cn.wenzhuo4657.BigMarket.tigger.api.dto.ActivityDrawRequestDTO;
import cn.wenzhuo4657.BigMarket.types.annotations.DCCValue;
import cn.wenzhuo4657.BigMarket.types.annotations.RateLimiterAccessInterceptor;
import com.alibaba.nacos.shaded.com.google.common.util.concurrent.RateLimiter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/19
 * @description:
 */

@Slf4j
@Aspect
@Component
public class RateLimiterAOP {


    @DCCValue("rateLimiterSwitch:close")
    private String rateLimiterSwitch;//总开关


    //      个人限频一分钟
    private final Cache<String, RateLimiter> loginRecord = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    // 个人限频黑名单24h - 分布式业务场景，可以记录到 Redis 中
    private final Cache<String, Long> blacklist = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();


    @Pointcut("@annotation(cn.wenzhuo4657.BigMarket.types.annotations.RateLimiterAccessInterceptor)")
    public void aopPoint() {
    }


    @Around("aopPoint()&& @annotation(rateLimiterAccessInterceptor)")
    public Object doRouter(ProceedingJoinPoint jp, RateLimiterAccessInterceptor rateLimiterAccessInterceptor) throws Throwable {
        if (StringUtils.isBlank(rateLimiterSwitch) || "close".equals(rateLimiterSwitch)) {
            return jp.proceed();
        }
        String key = rateLimiterAccessInterceptor.key();
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("annotation RateLimiter uId is null！");
        }
//        找到拦截器路由值
        /**
         *   des: 该路由属性attrValue相当于请求的唯一标识，
        */
        String attrValue = getAttrValue(key, jp.getArgs());
        log.info("aop attr {}", attrValue);


        if (!"all".equals(attrValue) && rateLimiterAccessInterceptor.blacklistCount() != 0 &&null !=blacklist.getIfPresent(attrValue)&&blacklist.getIfPresent(attrValue) > rateLimiterAccessInterceptor.blacklistCount()){
            log.info("限流-黑名单拦截(24h)：{}", attrValue);
            return fallbackMethodResult(jp, rateLimiterAccessInterceptor.fallbackMethod());
        }

//        1，得到限流器
        RateLimiter rateLimiter = loginRecord.getIfPresent(attrValue);
        if (null == rateLimiter) {
            rateLimiter = RateLimiter.create(rateLimiterAccessInterceptor.permitsPerSecond());
            loginRecord.put(attrValue, rateLimiter);
        }

//        2，进行拦截

        if (!rateLimiter.tryAcquire()){
            if (rateLimiterAccessInterceptor.blacklistCount() != 0){
                if (null == blacklist.getIfPresent(attrValue)) {
                    blacklist.put(attrValue, 1L);
                } else {
                    blacklist.put(attrValue, blacklist.getIfPresent(attrValue) + 1L);
                }
            }
            log.info("限流-超频次拦截：{}", attrValue);
            return fallbackMethodResult(jp, rateLimiterAccessInterceptor.fallbackMethod());
        }

        return jp.proceed();
    }

    private Object fallbackMethodResult(ProceedingJoinPoint jp, String fallbackMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
//        从方法调用处（类）获取用户配置的拦截方法并执行。
//        getDeclaredMethod可以找到所有访问权限的方法，
        Method method = jp.getTarget().getClass().getDeclaredMethod(fallbackMethod, ActivityDrawRequestDTO.class);
        method.setAccessible(true);
        Object obj = method.invoke(jp.getThis(), jp.getArgs());
        method.setAccessible(false);
        return  obj;
    }

    /**
     * @author:wenzhuo4657 des: 获取attr的值为属性名称的值，这意味着该名称在args对象的属性集合中不唯一，生效取决于其顺序，不会全部遍历。
     */
    public String getAttrValue(String attr, Object[] args) {
        if (args[0] instanceof String) {
            return args[0].toString();
        }
        String fieldValue = null;
        for (Object arg : args) {
            try {
                if (StringUtils.isNotBlank(fieldValue)) {
                    break;
                }
                fieldValue = String.valueOf(this.getValueByName(arg, attr));
            } catch (Exception e) {
                log.error("获取路由属性值失败 attr：{}", attr, e);
            }
        }
        return fieldValue;
    }

    private Object getValueByName(Object item, String name) {
        try {
            Field field = getFieldByName(item, name);
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            Object o = field.get(item);
            field.setAccessible(false);
            return o;
        } catch (IllegalAccessException e) {
            return null;
        }

    }

    private Field getFieldByName(Object item, String name) {
        try {
            Field field;
            try {
                field = item.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                field = item.getClass().getSuperclass().getDeclaredField(name);
            }
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }


}
