package cn.wenzhuo4657.LuckySphere.querys.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 抽奖活动订单
 */
@Getter
@Builder
@Document(indexName = "big_market.raffle_activity_order")
public class RaffleActivityOrderVO {

    @Id
    private  String id;

    /**
     * 用户ID
     */
    @Field(name = "_user_id")
    private String userId;

    /**
     * sku
     */
    @Field(name = "_sku")
    private Long sku;

    /**
     * 活动ID
     */
    @Field(name = "_activity_id")
    private Long activityId;

    /**
     * 活动名称
     */
    @Field(name = "_activity_name")
    private String activityName;

    /**
     * 抽奖策略ID
     */
    @Field(name = "_strategy_id")
    private Long strategyId;

    /**
     * 订单ID
     */
    @Field(name = "_order_id")
    private String orderId;

    /**
     * 下单时间
     */
    @Field(name = "_order_time",type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date orderTime;

    /**
     * 总次数
     */
    @Field(name = "_total_count")
    private Integer totalCount;

    /**
     * 日次数
     */
    @Field(name = "_day_count")
    private Integer dayCount;

    /**
     * 月次数
     */
    @Field(name = "_month_count")
    private Integer monthCount;

    /**
     * 支付金额【积分】
     */
    @Field(name = "_pay_amount")
    private BigDecimal payAmount;

    /**
     * 订单状态
     */
    @Field(name = "_state")
    private String state;

    /**
     * 业务仿重ID - 外部透传的，确保幂等
     */
    @Field(name = "_out_business_no")
    private String outBusinessNo;

    /**
     * 创建时间
     */
    @Field(name = "_create_time",type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(name = "_update_time",type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date updateTime;
}
