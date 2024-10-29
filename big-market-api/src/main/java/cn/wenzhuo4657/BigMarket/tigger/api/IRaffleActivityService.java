package cn.wenzhuo4657.BigMarket.tigger.api;

import cn.wenzhuo4657.BigMarket.tigger.api.dto.ActivityDrawRequestDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.ActivityDrawResponseDTO;
import cn.wenzhuo4657.BigMarket.types.models.Response;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/29
 * @description: 抽奖活动服务接口
 */
public interface IRaffleActivityService {
    /**
     * 活动装配，数据预热缓存
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * 活动抽奖接口
     * @param request 请求对象
     * @return 返回结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);
}
