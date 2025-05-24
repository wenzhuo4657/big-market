package cn.wenzhuo4657.BigMarket.querys.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


/**
 * 用户行为返利订单
 */
@Getter
@Builder
@Document(indexName = "big_market.user_behavior_rebate_order")
public class UserBehaviorRebateOrderVO {

    /**
     * 自增ID
     */
    @Id
    private long id;
    /**
     * 用户ID
     */
    @Field(name = "_user_id")
    private String userId;
    /**
     * 订单ID
     */
    @Field(name = "_order_id")
    private String orderId;
    /**
     * 行为类型（sign 签到、openai_pay 支付）
     */
    @Field(name = "_behavior_type")
    private String behaviorType;
    /**
     * 返利描述
     */
    @Field(name = "_rebate_desc")
    private String rebateDesc;
    /**
     * 返利类型（sku 活动库存充值商品、integral 用户活动积分）
     */
    @Field(name = "_rebate_type")
    private String rebateType;
    /**
     * 返利配置【sku值，积分值】
     */
    @Field(name = "_rebate_config")
    private String rebateConfig;
    /**
     * 业务ID - 拼接的唯一值
     */
    @Field(name = "_biz_id")
    private String bizId;
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

    /** 业务仿重ID - 外部透传，方便查询使用 */
    @Field(name = "_out_business_no")
    private String outBusinessNo;
}
