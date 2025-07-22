package cn.wenzhuo4657.LuckySphere.trigger.http;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.*;
import cn.wenzhuo4657.LuckySphere.domain.activity.model.valobj.OrderTradeTypeVO;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.IRaffleActivityPartakeService;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.IRaffleActivitySkuProductService;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.armory.IActivityArmory;
import cn.wenzhuo4657.LuckySphere.domain.auth.service.IAuthService;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserAwardRecordEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.valobj.AwardStateVO;
import cn.wenzhuo4657.LuckySphere.domain.award.service.IAwardService;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.entity.TradeEntity;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.valobj.TradeNameVO;
import cn.wenzhuo4657.LuckySphere.domain.credit.model.valobj.TradeTypeVO;
import cn.wenzhuo4657.LuckySphere.domain.credit.service.ICreditAdjustService;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj.BehaviorTypeVO;
import cn.wenzhuo4657.LuckySphere.domain.rebate.service.IBehaviorRebateService;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.RaffleAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.IRaffleStrategy;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.armory.IStrategyArmory;
import cn.wenzhuo4657.LuckySphere.tigger.api.IRaffleActivityService;
import cn.wenzhuo4657.LuckySphere.tigger.api.dto.*;
import cn.wenzhuo4657.LuckySphere.types.annotations.DCCValue;
import cn.wenzhuo4657.LuckySphere.types.annotations.RateLimiterAccessInterceptor;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import cn.wenzhuo4657.LuckySphere.tigger.api.reponse.Response;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cn.wenzhuo4657.LuckySphere.config.PrometheusCustomMetricsConfig;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/29
 * @description:
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/${app.config.api-version}/raffle/activity/")
@DubboService(version = "1.0")
public class RaffleActivityController implements IRaffleActivityService {
    private final SimpleDateFormat dateFormatDay=new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");

    @Resource
    private IRaffleActivityPartakeService raffleActivityPartakeService;
    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private IRaffleActivitySkuProductService raffleActivitySkuProductService;
    @Resource
    private IAwardService awardService;
    @Resource
    private IActivityArmory activityArmory;
    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IBehaviorRebateService behaviorRebateService;

    @Resource
    private ICreditAdjustService creditAdjustService;

    @Resource
    private IAuthService authService;


    @DCCValue("degradeSwitch:close")
    private String degradeSwitch;

    @Override
    @RequestMapping(value = "armory", method = RequestMethod.GET)
    public Response<Boolean> armory(@RequestParam Long activityId) {
        try {
            log.info("活动装配，数据预热，开始 activityId:{}", activityId);
            activityArmory.assembleActivitySkuByActivityId(activityId);
            strategyArmory.assembleLotteryStrategyByActivityId(activityId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
            log.info("活动装配，数据预热，完成 activityId:{}", activityId);
            return response;
        } catch (Exception e) {
            log.error("活动装配，数据预热，失败 activityId:{}", activityId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }

}

    @Override
//    一次请求的安全策略，执行时间超过150毫秒，则熔断
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "150")
    }, fallbackMethod = "drawHystrixError")
//    用户维度的熔断，默认1秒内超过1次请求，则熔断，短时间多次熔断则加入黑名单
    @RateLimiterAccessInterceptor(key = "userId",fallbackMethod = "drawRateLimiterError", permitsPerSecond = 1.0d, blacklistCount = 1)
    @RequestMapping(value = "draw", method = RequestMethod.POST)
    public Response<ActivityDrawResponseDTO> draw(@RequestBody  ActivityDrawRequestDTO request) {
        PrometheusCustomMetricsConfig.incrementHttpServerRequestsTotal();
        try {
            log.info("活动抽奖 userId:{} activityId:{}", request.getUserId(), request.getActivityId());
            if (StringUtils.isBlank(request.getUserId())|| Objects.isNull(request.getActivityId())){
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }
            UserRaffleOrderEntity orderEntity = raffleActivityPartakeService.createOrder(request.getUserId(), request.getActivityId());
            log.info("活动抽奖，创建订单 userId:{} activityId:{} orderId:{}", request.getUserId(), request.getActivityId(), orderEntity.getOrderId());
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(
                    RaffleFactorEntity.builder()
                            .userId(request.getUserId())
                            .strategyId(orderEntity.getStrategyId())
                            .endDateTime(orderEntity.getEndDateTime())
                            .build()
            );
            UserAwardRecordEntity userAwardRecord = UserAwardRecordEntity.builder()
                    .id(orderEntity.getId())
                    .userId(orderEntity.getUserId())
                    .activityId(orderEntity.getActivityId())
                    .strategyId(orderEntity.getStrategyId())
                    .orderId(orderEntity.getOrderId())
                    .awardId(raffleAwardEntity.getAwardId())
                    .awardTitle(raffleAwardEntity.getAwardTitle())
                    .awardTime(new Date())
                    .awardState(AwardStateVO.create)
                    .awardConfig(raffleAwardEntity.getAwardConfig())
                    .build();

//            写入中奖记录和任务记录
            awardService.saveUserAwardRecord(userAwardRecord);

            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(ActivityDrawResponseDTO.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardTitle(raffleAwardEntity.getAwardTitle())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();

        }catch (AppException e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }

    }
    private Response<ActivityDrawResponseDTO> drawRateLimiterError( ActivityDrawRequestDTO request) {
        log.info("活动抽奖限流 userId:{} activityId:{}", request.getUserId(), request.getActivityId());
        return Response.<ActivityDrawResponseDTO>builder()
                .code(ResponseCode.RATE_LIMITER.getCode())
                .info(ResponseCode.RATE_LIMITER.getInfo())
                .build();
    }

    private Response<ActivityDrawResponseDTO> drawHystrixError( ActivityDrawRequestDTO request) {
        log.info("活动抽奖熔断 userId:{} activityId:{}", request.getUserId(), request.getActivityId());
        return Response.<ActivityDrawResponseDTO>builder()
                .code(ResponseCode.HYSTRIX.getCode())
                .info(ResponseCode.HYSTRIX.getInfo())
                .build();
    }

    @Override
    @RequestMapping(value = "calendar_sign_rebate",method = RequestMethod.POST)
    public Response<Boolean> calendarSignRebate(@RequestParam String userId) {
        try {
            log.info("日历签到返利开始 userId:{}", userId);
            BehaviorEntity behaviorEntity = new BehaviorEntity();
            behaviorEntity.setUserId(userId);
            behaviorEntity.setBehaviorTypeVO(BehaviorTypeVO.SIGN);
            behaviorEntity.setOutBusinessNo(dateFormatDay.format(new Date()));
            List<String> orderIds = behaviorRebateService.createOrder(behaviorEntity);
            log.info("日历签到返利完成 userId:{} orderIds: {}", userId, JSON.toJSONString(orderIds));
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
        }  catch (AppException e) {
            log.error("日历签到返利异常 userId:{} ", userId, e);
            return Response.<Boolean>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("日历签到返利失败 userId:{}", userId);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data(false)
                    .build();
        }
    }


    @Override
    @RequestMapping(value = "is_calendar_sign_rebate", method = RequestMethod.POST)
    public Response<Boolean> isCalendarSignRebate(@RequestParam String userId) {
        try {
            log.info("查询用户是否完成日历签到返利开始 userId:{}", userId);
            String outBusinessNo=dateFormatDay.format(new Date());
            List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = behaviorRebateService.queryOrderByOutBusinessNo(userId, outBusinessNo);
            log.info("查询用户是否完成日历签到返利完成 userId:{} orders.size:{}", userId, behaviorRebateOrderEntities.size());
            return  Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    /**
                     *  @author:wenzhuo4657
                        des:由于目前行为返利只有签到行为，所以只要不为空就判定已经签到完成了
                    */
                    .data(!behaviorRebateOrderEntities.isEmpty())
                    .build();
        } catch (Exception e) {
            log.error("查询用户是否完成日历签到返利失败 userId:{}", userId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data(false)
                    .build();
        }
    }
    /**
     *  @author:wenzhuo4657
        des:
     查询账户额度，
    */

    @RequestMapping(value = "query_user_activity_account", method = RequestMethod.POST)
    @Override
    public Response<UserActivityAccountResponseDTO> queryUserActivityAccount(@RequestBody UserActivityAccountRequestDTO request) {
        try{
            log.info("查询用户活动账户开始 userId:{} activityId:{}", request.getUserId(), request.getActivityId());
            if (StringUtils.isBlank(request.getUserId()) || null==request.getActivityId()){
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }
            ActivityAccountEntity activityAccountEntity=raffleActivityAccountQuotaService.queryActivityAccountEntity(request.getActivityId(), request.getUserId());



            UserActivityAccountResponseDTO userActivityAccountResponseDTO = UserActivityAccountResponseDTO.builder()
                    .totalCount(activityAccountEntity.getTotalCount())
                    .totalCountSurplus(activityAccountEntity.getTotalCountSurplus())
                    .dayCount(activityAccountEntity.getDayCount())
                    .dayCountSurplus(activityAccountEntity.getDayCountSurplus())
                    .monthCount(activityAccountEntity.getMonthCount())
                    .monthCountSurplus(activityAccountEntity.getMonthCountSurplus())
                    .build();
            log.info("查询用户活动账户完成 userId:{} activityId:{} dto:{}", request.getUserId(), request.getActivityId(), JSON.toJSONString(userActivityAccountResponseDTO));
            return Response.<UserActivityAccountResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(userActivityAccountResponseDTO)
                    .build();
        } catch (Exception e) {
            log.error("查询用户活动账户失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<UserActivityAccountResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }


    /**
     *  @author:wenzhuo4657
        des:
     curl -X POST  "activityId=100301" http://localhost:8091/api/v1/raffle/activity/query_sku_product_list_by_activity_id

    */
    @RequestMapping(value = "query_sku_product_list_by_activity_id",method = RequestMethod.POST)
    @Override
    public Response<List<SkuProductResponseDTO>> querySkuProductListByActivityId(@RequestParam Long activityId) {
        try{
            log.info("查询sku商品集合开始 activityId:{}", activityId);
            if (null == activityId) {
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }

            List<SkuProductEntity> skuProductEntityList = raffleActivitySkuProductService.querySkuProductEntityListByActivityId(activityId);
            List<SkuProductResponseDTO> skuProductResponseDTOList=new ArrayList<>(skuProductEntityList.size());
            for (SkuProductEntity skuProductEntity:skuProductEntityList){
                SkuProductResponseDTO.ActivityCount activityCount = new SkuProductResponseDTO.ActivityCount();
                activityCount.setTotalCount(skuProductEntity.getActivityCount().getTotalCount());
                activityCount.setMonthCount(skuProductEntity.getActivityCount().getMonthCount());
                activityCount.setDayCount(skuProductEntity.getActivityCount().getDayCount());

                SkuProductResponseDTO skuProductResponseDTO = new SkuProductResponseDTO();
                skuProductResponseDTO.setSku(skuProductEntity.getSku());
                skuProductResponseDTO.setActivityId(skuProductEntity.getActivityId());
                skuProductResponseDTO.setActivityCountId(skuProductEntity.getActivityCountId());
                skuProductResponseDTO.setStockCount(skuProductEntity.getStockCount());
                skuProductResponseDTO.setStockCountSurplus(skuProductEntity.getStockCountSurplus());
                skuProductResponseDTO.setProductAmount(skuProductEntity.getProductAmount());
                skuProductResponseDTO.setActivityCount(activityCount);
                skuProductResponseDTOList.add(skuProductResponseDTO);

            }

            log.info("查询sku商品集合完成 activityId:{} skuProductResponseDTOS:{}", activityId, JSON.toJSONString(skuProductResponseDTOList));
            return Response.<List<SkuProductResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(skuProductResponseDTOList)
                    .build();

        }catch (Exception e) {
            log.error("查询sku商品集合失败 activityId:{}", activityId, e);
            return Response.<List<SkuProductResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
    @RequestMapping(value = "query_user_credit_account", method = RequestMethod.POST)
    @Override
    public Response<BigDecimal> queryUserCreditAccount(String userId) {
        try {
            log.info("查询用户积分值开始 userId:{}", userId);
            CreditAccountEntity creditAccountEntity = creditAdjustService.queryUserCreditAccount(userId);
            log.info("查询用户积分值完成 userId:{} adjustAmount:{}", userId, creditAccountEntity.getAdjustAmount());
            return Response.<BigDecimal>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(creditAccountEntity.getAdjustAmount())
                    .build();
        } catch (Exception e) {
            log.error("查询用户积分值失败 userId:{}", userId, e);
            return Response.<BigDecimal>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     *  @author:wenzhuo4657
        des:
     此处增加限流限频的主要原因是为了避免业务防重失效，即便是uuid也是根据系统时间计算得出。
    */
    @Override
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "150")
    }, fallbackMethod = "drawHystrixError")
    @RateLimiterAccessInterceptor(key = "userId",fallbackMethod = "drawRateLimiterError", permitsPerSecond = 1.0d, blacklistCount = 1)
    @RequestMapping(value = "credit_pay_exchange_sku", method = RequestMethod.POST)
    public Response<Boolean> creditPayExchangeSku(@RequestBody SkuProductShopCartRequestDTO request) {
        PrometheusCustomMetricsConfig.incrementHttpServerRequestsTotal();
        try{
            log.info("积分兑换商品开始 userId:{} sku:{}", request.getUserId(), request.getSku());
            SkuRechargeEntity build = SkuRechargeEntity.builder().
                    sku(request.getSku())
                    .userId(request.getUserId())
                    .outBusinessNo(request.getUserId() + UUID.randomUUID().toString())
                    .orderTradeType(OrderTradeTypeVO.credit_pay_trade).build();

            if (StringUtils.isNotBlank(request.getOutBussion())){
                build.setOutBusinessNo(request.getOutBussion().trim());
            }

            UnpaidActivityOrderEntity skuRechargeOrder = raffleActivityAccountQuotaService.createSkuRechargeOrder(build);
            log.info("积分兑换商品，创建订单完成 userId:{} sku:{} outBusinessNo:{}", request.getUserId(), request.getSku(), skuRechargeOrder.getOutBusinessNo());
            String orderId = creditAdjustService.createOrder(TradeEntity.builder()
                    .userId(skuRechargeOrder.getUserId())
                    .tradeName(TradeNameVO.CONVERT_SKU)
                    .tradeType(TradeTypeVO.REVERSE)
                    .amount(skuRechargeOrder.getPayAmount())
                    .outBusinessNo(skuRechargeOrder.getOutBusinessNo())
                    .build());
            log.info("积分兑换商品，支付订单完成  userId:{} sku:{} orderId:{}", request.getUserId(), request.getSku(), orderId);
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();


        } catch (Exception e) {
            log.error("积分兑换商品失败 userId:{} sku:{}", request.getUserId(), request.getSku(), e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data(false)
                    .build();
        }
    }


//    ==============================token封装=========================================


    @Override
    @RequestMapping(value = "draw_by_token", method = RequestMethod.POST)
    public Response<ActivityDrawResponseDTO> draw(@RequestHeader("Authorization") String token,@RequestBody ActivityDrawRequestDTO request) {
        PrometheusCustomMetricsConfig.incrementHttpServerRequestsTotal();
        try {


            boolean success = authService.checkToken(token);
            if (!success) {
                return Response.<ActivityDrawResponseDTO>builder()
                        .code(ResponseCode.Login.TOKEN_ERROR.getCode())
                        .info(ResponseCode.Login.TOKEN_ERROR.getInfo())
                        .build();
            }
            String openid = authService.openid(token);
            assert null != openid;
            log.info("活动抽奖开始 - 解析用户ID userId:{}", openid);
            request.setUserId(openid);
            return draw(request);
        }catch (Exception e){
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @Override
    @RequestMapping(value = "calendar_sign_rebate_by_token",method = RequestMethod.POST)
    public Response<Boolean> calendarSignRebateByToken(@RequestHeader("Authorization")String token) {
        try {


            boolean success = authService.checkToken(token);
            if (!success) {
                return Response.<Boolean>builder()
                        .code(ResponseCode.Login.TOKEN_ERROR.getCode())
                        .info(ResponseCode.Login.TOKEN_ERROR.getInfo())
                        .build();
            }
            String openid = authService.openid(token);
            assert null != openid;
            log.info("执行签到开始 - 解析用户ID userId:{}", openid);
            return calendarSignRebate(openid);
        }catch (Exception e){
            log.error("执行签到失败", e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @Override
    @RequestMapping(value = "is_calendar_sign_rebate_by_token", method = RequestMethod.POST)
    public Response<Boolean> isCalendarSignRebateByToken(@RequestHeader("Authorization")String token) {
        try {
            boolean success = authService.checkToken(token);
            if (!success) {
                return Response.<Boolean>builder()
                        .code(ResponseCode.Login.TOKEN_ERROR.getCode())
                        .info(ResponseCode.Login.TOKEN_ERROR.getInfo())
                        .build();
            }
            String openid = authService.openid(token);
            assert null != openid;
            log.info("执行判断签到开始 - 解析用户ID userId:{}", openid);
            return isCalendarSignRebate(openid);
        }catch (Exception e){
            log.error("执行判断签到失败", e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @Override
    @RequestMapping(value = "query_user_activity_account_by_token", method = RequestMethod.POST)
    public Response<UserActivityAccountResponseDTO> queryUserActivityAccount(@RequestHeader("Authorization") String token, @RequestBody UserActivityAccountRequestDTO request) {
        PrometheusCustomMetricsConfig.incrementHttpServerRequestsTotal();
        try {
            // 1. Token 校验
            boolean success = authService.checkToken(token);
            if (!success) {
                return Response.<UserActivityAccountResponseDTO>builder()
                        .code(ResponseCode.Login.TOKEN_ERROR.getCode())
                        .info(ResponseCode.Login.TOKEN_ERROR.getInfo())
                        .build();
            }

            // 2. Token 解析
            String openid = authService.openid(token);
            assert null != openid;
            log.info("查询用户活动账户开始 - 解析用户ID userId:{}", openid);
            request.setUserId(openid);

            // 3. 执行签到
            return queryUserActivityAccount(request);
        } catch (Exception e) {
            log.error("查询用户活动账户失败", e);
            return Response.<UserActivityAccountResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @Override
    @RequestMapping(value = "query_user_credit_account_by_token", method = RequestMethod.POST)
    public Response<BigDecimal> queryUserCreditAccountByToken(@RequestHeader("Authorization") String token) {
        PrometheusCustomMetricsConfig.incrementHttpServerRequestsTotal();
        try {
            // 1. Token 校验
            boolean success = authService.checkToken(token);
            if (!success) {
                return Response.<BigDecimal>builder()
                        .code(ResponseCode.Login.TOKEN_ERROR.getCode())
                        .info(ResponseCode.Login.TOKEN_ERROR.getInfo())
                        .build();
            }

            // 2. Token 解析
            String openid = authService.openid(token);
            assert null != openid;
            log.info("查询用户积分值开始 - 解析用户ID userId:{}", openid);


            return queryUserCreditAccount(openid);
        } catch (Exception e) {
            log.error("查询用户积分值失败", e);
            return Response.<BigDecimal>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }




    @Override
    @RequestMapping(value = "credit_pay_exchange_sku_by_token", method = RequestMethod.POST)
    public Response<Boolean> creditPayExchangeSku(@RequestHeader("Authorization") String token, @RequestBody SkuProductShopCartRequestDTO request) {
        PrometheusCustomMetricsConfig.incrementHttpServerRequestsTotal();
        try {
            // 1. Token 校验
            boolean success = authService.checkToken(token);
            if (!success) {
                return Response.<Boolean>builder()
                        .code(ResponseCode.Login.TOKEN_ERROR.getCode())
                        .info(ResponseCode.Login.TOKEN_ERROR.getInfo())
                        .build();
            }

            // 2. Token 解析
            String openid = authService.openid(token);
            assert null != openid;
            log.info("积分兑换商品开始 - 解析用户ID userId:{}", openid);
            request.setUserId(openid);

            /**
             *  @author:wenzhuo4657
                des:   需要外部透传业务唯一id,否则由于消息重试机制无法保证业务唯一。
            */
            if (StringUtils.isBlank(request.getOutBussion())){
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }
            return creditPayExchangeSku(request);
        } catch (Exception e) {
            log.error("积分兑换商品失败", e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
