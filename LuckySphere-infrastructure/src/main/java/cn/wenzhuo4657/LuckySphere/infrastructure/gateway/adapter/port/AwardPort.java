package cn.wenzhuo4657.LuckySphere.infrastructure.gateway.adapter.port;

import cn.wenzhuo4657.LuckySphere.domain.award.adapter.port.IAwardPort;
import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.IOpenAIAccountService;
import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.dto.AdjustQuotaRequestDTO;
import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.dto.AdjustQuotaResponseDTO;
import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.reponse.Response;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description:
 */
@Slf4j
@Service
public class AwardPort implements IAwardPort {

    @Value("${gateway.config.big-market-appId}")
    private String BIG_MARKET_APPID;
    @Value("${gateway.config.big-market-appToken}")
    private String BIG_MARKET_APPTOKEN;

    @Resource
    private IOpenAIAccountService openAIAccountService;
    @Override
    public void adjustAmount(String userId, Integer increaseQuota) throws Exception {

        try{
            AdjustQuotaRequestDTO requestDTO = AdjustQuotaRequestDTO.builder()
                    .appId(BIG_MARKET_APPID)
                    .appToken(BIG_MARKET_APPTOKEN)
                    .openid(userId)
                    .increaseQuota(increaseQuota)
                    .build();
            Call<Response<AdjustQuotaResponseDTO>> responseCall = openAIAccountService.adjustQuota(requestDTO);
            Response<AdjustQuotaResponseDTO> body = responseCall.execute().body();

            log.info("请求OpenAI应用账户调额接口完成 userId:{} increaseQuota:{} response:{}", userId, increaseQuota, JSON.toJSONString(body));


            if (null==body||null ==body.getCode()||!"0000".equals(body.getCode())){
                throw new AppException(ResponseCode.GATEWAY_ERROR.getCode(), ResponseCode.GATEWAY_ERROR.getInfo());
            }
        } catch (Exception e) {
            log.error("请求OpenAI应用账户调额接口失败 userId:{} increaseQuota:{}", userId, increaseQuota, e);
            throw e;
        }

    }
}
