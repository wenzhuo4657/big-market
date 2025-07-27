module LuckySphere.infrastructure {
    exports cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;
    exports cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po;
    exports cn.wenzhuo4657.LuckySphere.infrastructure.gateway;


    requires org.mybatis;
    requires spring.beans;
    requires com.alibaba.fastjson2;
    requires static lombok;
    requires spring.context;
    requires retrofit2;
    requires java.annotation;
    requires fastjson;
    requires rocketmq.spring.boot;
    requires spring.data.commons;




    requires LuckySphere.domain;
    requires LuckySphere.types;
    requires spring.tx;
    requires redisson;
    requires org.checkerframework.checker.qual;
    requires java.logging;
    requires mybatis.plus.annotation;
    requires annotations;

}