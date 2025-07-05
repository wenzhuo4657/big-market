package cn.wenzhuo4657.LuckySphere.querys.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 用户奖品记录
 */
@Getter
@Builder
@Document(indexName = "big_market.user_award_record")
public class UserAwardRecordVO  {

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
     * 活动ID
     */
    @Field(name = "_activity_id")
    private Long activityId;
    /**
     * 抽奖策略ID
     */
    @Field(name = "_strategy_id")
    private Long strategyId;
    /**
     * 抽奖订单ID【作为幂等使用】
     */
    @Field(name = "_order_id")
    private String orderId;
    /**
     * 奖品ID
     */
    @Field(name = "_award_id")
    private Integer awardId;
    /**
     * 奖品标题（名称）
     */
    @Field(name = "_award_title")
    private String awardTitle;
    /**
     * 中奖时间
     */
    @Field(name = "_award_time",type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date awardTime;
    /**
     * 奖品状态；create-创建、completed-发奖完成
     */
    @Field(name = "_award_state")
    private String awardState;
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

