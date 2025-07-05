package cn.wenzhuo4657.LuckySphere.infrastructure.gateway;

import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.dto.AdjustQuotaRequestDTO;
import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.dto.AdjustQuotaResponseDTO;
import cn.wenzhuo4657.LuckySphere.infrastructure.gateway.reponse.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/2
 * @description:  openAI项目接口外部对接服务
 */
public interface IOpenAIAccountService {



    @POST("/api/v1/openai/account/adjust_quota")
    Call<Response<AdjustQuotaResponseDTO>> adjustQuota(@Body AdjustQuotaRequestDTO requestDTO);
}
