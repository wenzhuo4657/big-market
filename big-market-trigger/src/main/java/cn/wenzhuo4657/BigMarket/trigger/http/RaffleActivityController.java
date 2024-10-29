package cn.wenzhuo4657.BigMarket.trigger.http;

import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivityPartakeService;
import cn.wenzhuo4657.BigMarket.domain.activity.service.armory.IActivityArmory;
import cn.wenzhuo4657.BigMarket.domain.award.service.IAwardService;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyArmory;
import cn.wenzhuo4657.BigMarket.tigger.api.IRaffleActivityService;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.ActivityDrawRequestDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.ActivityDrawResponseDTO;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/29
 * @description:
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/${app.config.api-version}/raffle/activity/")
public class RaffleActivityController implements IRaffleActivityService {

    @Resource
    private IRaffleActivityPartakeService raffleActivityPartakeService;
    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private IAwardService awardService;
    @Resource
    private IActivityArmory activityArmory;
    @Resource
    private IStrategyArmory strategyArmory;
    @Override
    @RequestMapping(value = "armory", method = RequestMethod.GET)
    public Response<Boolean> armory(Long activityId) {
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
    public Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request) {

        return null;
    }
}
