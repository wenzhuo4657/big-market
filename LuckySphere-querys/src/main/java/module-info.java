module LuckySphere.querys {
    requires spring.data.commons;
    requires spring.beans;
    requires elasticsearch.rest.high.level.client;
    requires spring.data.elasticsearch;
    requires spring.context;
    requires static lombok;
    requires com.fasterxml.jackson.annotation;
    opens cn.wenzhuo4657.LuckySphere.querys.repository;
    exports cn.wenzhuo4657.LuckySphere.querys.entity;
    exports cn.wenzhuo4657.LuckySphere.querys.repository;
}