package cn.wenzhuo4657.BigMarket.querys.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 用户抽奖订单
 */
@Getter
@Builder
@Document(indexName = "big_market.user_raffle_order")
public class    UserRaffleOrderVO  {
    @Id
    private  String id;

    /** 用户ID */
    @Field(name = "_user_id")
    private String userId;
    /** 活动ID */
    @Field(name = "_activity_id")
    private Long activityId;
    /** 活动名称 */
    @Field(name = "_activity_name")
    private String activityName;
    /** 抽奖策略ID */
    @Field(name = "_strategy_id")
    private Long strategyId;
    /** 订单ID */
    @Field(name = "_order_id")
    private String orderId;
    /** 下单时间 */
    @Field(name = "_order_time",type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date orderTime;
    /** 订单状态；create-创建、used-已使用、cancel-已作废 */
    @Field(name = "_order_state")
    private String orderState;
    /** 创建时间 */

    @Field(name = "_create_time",type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createTime;
    /** 更新时间 */

    @Field(name = "_update_time",type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date updateTime;
}
