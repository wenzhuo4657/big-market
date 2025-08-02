package cn.wenzhuo4657.LuckySphere.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "调用成功"),
    UN_ERROR("0001", "调用失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    INDEX_DUP("0003", "唯一索引冲突|创建的太快了，请慢一点"),
    RATE_LIMITER("0005", "访问限流拦截"),
    HYSTRIX("0006", "访问熔断拦截"),
    GATEWAY_ERROR("0007", "网关接口调用失败"),
    APP_TOKEN_ERROR("0008", "接口权限拦截"),
    STRATEGY_RULE_WEIGHT_IS_NULL("ERR_BIZ_001", "业务异常，策略规则中 rule_weight 权重规则已适用但未配置"),
    UN_ASSEMBLED_STRATEGY_ARMORY("ERR_BIZ_002", "抽奖策略配置未装配，请通过IStrategyArmory完成装配"),
    ACTIVITY_STATE_ERROR("ERR_BIZ_003", "活动未开启（非open状态）"),
    ACTIVITY_DATE_ERROR("ERR_BIZ_004", "非活动日期范围"),
    ACTIVITY_SKU_STOCK_ERROR("ERR_BIZ_005", "活动库存不足"),

    ACCOUNT_QUOTA_ERROR("ERR_BIZ_006","账户总额度不足"),
    ACCOUNT_MONTH_QUOTA_ERROR("ERR_BIZ_007","账户月额度不足"),
    ACCOUNT_DAY_QUOTA_ERROR("ERR_BIZ_008","账户日额度不足"),
    ACTIVITY_ORDER_ERROR("ERR_BIZ_009", "用户抽奖单已使用过，不可重复抽奖"),
    CREDIT_ACCOUNT_QUOTA_ERROR("ERR_BIZ_010","积分账户余额不足"),
    USER_CREDIT_ACCOUNT_NOT_EXIST("ERR_BIZ_011","积分账户不存在"),
    CALENDAR_SIGNREBATE_ERROR("ERR_BIZ_012","请勿重复签到")

    ;

    private String code;
    private String info;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum Login {
        TOKEN_ERROR("0003", "登录权限拦截"),

        ;

        private String code;
        private String info;
    }
}
