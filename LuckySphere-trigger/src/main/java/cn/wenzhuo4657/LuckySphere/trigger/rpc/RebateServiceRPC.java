package cn.wenzhuo4657.LuckySphere.trigger.rpc;

import cn.wenzhuo4657.LuckySphere.domain.rebate.model.entity.BehaviorEntity;
import cn.wenzhuo4657.LuckySphere.domain.rebate.model.valobj.BehaviorTypeVO;
import cn.wenzhuo4657.LuckySphere.domain.rebate.service.IBehaviorRebateService;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.UserCreditAccountDao;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.UserCreditAccount;
import cn.wenzhuo4657.LuckySphere.tigger.api.IRebateService;
import cn.wenzhuo4657.LuckySphere.tigger.api.dto.PayCreditRequestDTO;
import cn.wenzhuo4657.LuckySphere.tigger.api.dto.RebateRequestDTO;
import cn.wenzhuo4657.LuckySphere.tigger.api.reponse.Request;
import cn.wenzhuo4657.LuckySphere.tigger.api.reponse.Response;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/4
 * @description: 返利服务
 */
@Slf4j
//@DubboService(version = "1.0",group = "LuckySphere")
public class RebateServiceRPC implements IRebateService {

    @Resource
    private Map<String, String> appTokenMap;

    @Resource
    private UserCreditAccountDao userCreditAccountDao;

    @Resource
    private IBehaviorRebateService behaviorRebateService;
    @Override
    public Response<Boolean> rebate(Request<RebateRequestDTO> request) {
        RebateRequestDTO requestDTO = request.getData();
        log.info("返利操作开始 userId:{} request:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO));

        try {
            //1,参数校验
            if (StringUtils.isBlank(requestDTO.getUserId())||StringUtils.isBlank(requestDTO.getBehaviorType())||StringUtils.isBlank(requestDTO.getOutBusinessNo())){
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
            }
            if (!request.getAppToken().equals(appTokenMap.get(request.getAppId()))) {
                throw new AppException(ResponseCode.APP_TOKEN_ERROR.getCode(), ResponseCode.APP_TOKEN_ERROR.getInfo());
            }
            BehaviorEntity behaviorEntity=new BehaviorEntity();
            behaviorEntity.setUserId(requestDTO.getUserId());
            behaviorEntity.setBehaviorTypeVO(BehaviorTypeVO.valueOf(requestDTO.getBehaviorType().toUpperCase()));
            behaviorEntity.setOutBusinessNo(requestDTO.getOutBusinessNo());
            List<String> orderIds = behaviorRebateService.createOrder(behaviorEntity);

            log.info("返利操作完成 userId:{} orderIds:{}", requestDTO.getUserId(), JSON.toJSONString(orderIds));
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
        } catch (AppException e) {
            log.error("返利操作异常 userId:{} ", requestDTO.getUserId(), e);
            return Response.<Boolean>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("返利操作失败 userId:{}", requestDTO.getUserId(), e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data(false)
                    .build();
        }

    }

    @Override
    public Response<Boolean> PayCredit(Request<PayCreditRequestDTO> request) {
        try {
            log.info("积分矢量操作-开始：userid: {} quota:{}",request.getData().getOpenID(),request.getData().getQuota());
            if (!request.getAppToken().equals(appTokenMap.get(request.getAppId()))) {
                throw new AppException(ResponseCode.APP_TOKEN_ERROR.getCode(), ResponseCode.APP_TOKEN_ERROR.getInfo());
            }

            UserCreditAccount userCreditAccountReq=new UserCreditAccount();
            userCreditAccountReq.setUserId(request.getData().getOpenID());
            userCreditAccountReq.setTotalAmount(new BigDecimal(request.getData().getQuota()));
            userCreditAccountReq.setAvailableAmount(new BigDecimal(request.getData().getQuota()));
            userCreditAccountDao.updateAddAmount(userCreditAccountReq);
            log.info("积分矢量操作-成功：userid: {} quota:{}",request.getData().getOpenID(),request.getData().getQuota());
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
        } catch (Exception e) {
            log.error("积分矢量操作-失败：userid: {} quota:{}",request.getData().getOpenID(),request.getData().getQuota(),e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data(false)
                    .build();

        }

    }
}
