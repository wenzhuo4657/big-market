package cn.wenzhuo4657.BigMarket.domain.strategy.service.annotation;

import cn.wenzhuo4657.BigMarket.domain.strategy.service.rule.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: LogicStrategy
 * @author: wenzhuo4657
 * @date: 2024/9/26
 * @Version: 1.0
 * @description:
 * 该注解的作用表示不同filer的类型，或者说其校验的权限规则，
 * 第一是起到一个标识和解释的作用，
 * 第二是帮助其传参，由于对于不同规则某些字段的解析方式不同。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy {

    DefaultLogicFactory.LogicModel logicMode();
}
