module LuckySphere.trigger {
    requires LuckySphere.api;
    requires LuckySphere.querys;
    requires spring.web;
    requires curator.framework;
    requires zookeeper.jute;
    requires hystrix.javanica;
    requires dubbo;
    requires xxl.job.core;
    requires com.alibaba.fastjson2;
    requires LuckySphere.infrastructure;
    requires LuckySphere.domain;
    requires LuckySphere.types;
    requires fastjson;
    requires static lombok;
    requires org.apache.commons.lang3;
    requires java.annotation;
}