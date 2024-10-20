package cn.wenzhuo4657.BigMarket.types.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: 基本事件
 */
@Data
public abstract class BaseEvent<T> {
    public abstract EventMessage<T> buildEventMessage(T data);
    public abstract String topic();//事件主题


    /**
     *  @author:wenzhuo4657
        des: 事件描述类
    */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventMessage<T> {
        private String id;
        private Date timestamp;
        private T data;
    }

}
