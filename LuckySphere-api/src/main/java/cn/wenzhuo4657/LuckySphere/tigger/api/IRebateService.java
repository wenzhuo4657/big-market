package cn.wenzhuo4657.LuckySphere.tigger.api;

import cn.wenzhuo4657.LuckySphere.tigger.api.dto.PayCreditRequestDTO;
import cn.wenzhuo4657.LuckySphere.tigger.api.dto.RebateRequestDTO;
import cn.wenzhuo4657.LuckySphere.tigger.api.reponse.Request;
import cn.wenzhuo4657.LuckySphere.tigger.api.reponse.Response;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/4
 * @description: 返利服务，（微服务dubbo发布）
 */
public interface IRebateService {
    Response<Boolean> rebate(Request<RebateRequestDTO> request);
    Response<Boolean> PayCredit(Request<PayCreditRequestDTO> request);

}
