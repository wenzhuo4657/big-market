module LuckySphere.domain {
    requires jjwt;
    requires spring.context;
    requires com.auth0.jwt;
    requires org.apache.commons.codec;
    requires LuckySphere.types;
    requires org.apache.commons.lang3;
    requires spring.beans;
    requires static lombok;
    requires fastjson;
    requires java.annotation;
    requires spring.web;


//    strategy领域
    exports cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity;
    exports cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj;
    exports cn.wenzhuo4657.LuckySphere.domain.strategy.repository;
    exports cn.wenzhuo4657.LuckySphere.domain.strategy.service;



//    activity领域
    exports cn.wenzhuo4657.LuckySphere.domain.activity.model.entity;
    exports cn.wenzhuo4657.LuckySphere.domain.activity.model.valobj;
    exports cn.wenzhuo4657.LuckySphere.domain.activity.model.aggregate;
    exports cn.wenzhuo4657.LuckySphere.domain.activity.repository;
    exports cn.wenzhuo4657.LuckySphere.domain.activity.service;
    exports cn.wenzhuo4657.LuckySphere.domain.activity.evnet;


//    award领域
    exports cn.wenzhuo4657.LuckySphere.domain.award.model.entity;
    exports cn.wenzhuo4657.LuckySphere.domain.award.model.valobj;
    exports cn.wenzhuo4657.LuckySphere.domain.award.model.aggregate;
    exports cn.wenzhuo4657.LuckySphere.domain.award.repository;
    exports cn.wenzhuo4657.LuckySphere.domain.award.service;
    exports cn.wenzhuo4657.LuckySphere.domain.award.adapter.port;


//    auth领域
    exports cn.wenzhuo4657.LuckySphere.domain.auth.service;


//    rebate领域
    exports cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity;
    exports cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj;
    exports cn.wenzhuo4657.LuckySphere.domain.rebate.model.aggregate;
    exports cn.wenzhuo4657.LuckySphere.domain.rebate.repository;
    exports cn.wenzhuo4657.LuckySphere.domain.rebate.service;
    exports cn.wenzhuo4657.LuckySphere.domain.rebate.event;


//    credit领域
    exports cn.wenzhuo4657.LuckySphere.domain.credit.model.entity;
    exports cn.wenzhuo4657.LuckySphere.domain.credit.model.valobj;
    exports cn.wenzhuo4657.LuckySphere.domain.credit.model.aggregate;
    exports cn.wenzhuo4657.LuckySphere.domain.credit.repository;
    exports cn.wenzhuo4657.LuckySphere.domain.credit.service;
    exports cn.wenzhuo4657.LuckySphere.domain.credit.event;

//    task领域
    exports cn.wenzhuo4657.LuckySphere.domain.task.model.entity;
    exports cn.wenzhuo4657.LuckySphere.domain.task.repository;
    exports cn.wenzhuo4657.LuckySphere.domain.task.service;

//    user领域
    exports cn.wenzhuo4657.LuckySphere.domain.user.model.entity;
    exports cn.wenzhuo4657.LuckySphere.domain.user.repository;
    exports cn.wenzhuo4657.LuckySphere.domain.user.service;
    exports cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.chain.factory;
    exports cn.wenzhuo4657.LuckySphere.domain.activity.service.armory;
    exports cn.wenzhuo4657.LuckySphere.domain.strategy.service.armory;
    exports cn.wenzhuo4657.LuckySphere.domain.award.event;


}