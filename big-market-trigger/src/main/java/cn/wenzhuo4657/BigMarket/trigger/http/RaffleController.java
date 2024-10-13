package cn.wenzhuo4657.BigMarket.trigger.http;

import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.RaffleFactorEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleAward;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.IRaffleStrategy;
import cn.wenzhuo4657.BigMarket.domain.strategy.service.armory.IStrategyArmory;
import cn.wenzhuo4657.BigMarket.tigger.api.IRaffleService;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleAwardListRequestDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleAwardListResponseDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleRequestDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleResponseDTO;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import cn.wenzhuo4657.BigMarket.types.models.Response;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/10
 * @description: 抽奖api
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/${app.config.api-version}/raffle/")
public class RaffleController implements IRaffleService {

    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private IRaffleAward raffleAward;

    @Resource
    private IStrategyArmory strategyArmory;


    @GetMapping(value = "strategy_armory")
    @Override
    public Response<Boolean> strategyArmory(Long strategyId) {
        try {


            log.info("抽奖策略装配开始 strategyId：{}", strategyId);
            boolean res = strategyArmory.assembleLotteryStrategy(strategyId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .info(ResponseCode.SUCCESS.getInfo())
                    .code(ResponseCode.SUCCESS.getCode())
                    .data(res).build();
            log.info("抽奖策略装配完成 strategyId：{} response: {}", strategyId, JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            Response<Boolean> response = Response.<Boolean>builder()
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .code(ResponseCode.UN_ERROR.getCode())
                    .build();
            return response;
        }
    }

    @PostMapping("query_raffle_award_list")
    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(@RequestBody RaffleAwardListRequestDTO requestDTO) {
        try{
            log.info("查询抽奖奖品列表配开始 strategyId：{}", requestDTO.getStrategyId());
            List<StrategyAwardEntity> strategyAwardEntities = raffleAward.queryRaffleStrategyAwardList(requestDTO.getStrategyId());
            List<RaffleAwardListResponseDTO> responseDTOS=new ArrayList<>(strategyAwardEntities.size());
            for (StrategyAwardEntity entity:strategyAwardEntities){
                responseDTOS.add(RaffleAwardListResponseDTO.builder()
                        .awardId(entity.getAwardId())
                        .awardSubtitle(entity.getAwardSubtitle())
                        .awardTitle(entity.getAwardTitle())
                        .sort(entity.getSort()).build());
            }
            Response<List<RaffleAwardListResponseDTO>> response = Response.<List<RaffleAwardListResponseDTO>>builder()
                    .info(ResponseCode.SUCCESS.getInfo())
                    .code(ResponseCode.SUCCESS.getCode())
                    .data(responseDTOS)
                    .build();
            log.info("查询抽奖奖品列表配置完成 strategyId：{} response: {}", requestDTO.getStrategyId(), JSON.toJSONString(response));
            return  response;
        } catch (Exception e) {
            log.error("查询抽奖奖品列表配置失败 strategyId：{}", requestDTO.getStrategyId(), e);
            return Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }

    }

    @RequestMapping(value = "random_raffle", method = RequestMethod.POST)
    @Override
    public Response<RaffleResponseDTO> randomRaffle(@RequestBody RaffleRequestDTO requestDTO) {
        try {
            log.info("随机抽奖开始 strategyId: {}", requestDTO.getStrategyId());
            RaffleAwardEntity result = raffleStrategy.performRaffle(RaffleFactorEntity.builder()
                    .userId("system")
                    .strategyId(requestDTO.getStrategyId()).build());
            Response<RaffleResponseDTO> response = Response.<RaffleResponseDTO>builder()
                    .info(ResponseCode.SUCCESS.getInfo())
                    .code(ResponseCode.SUCCESS.getCode())
                    .data(RaffleResponseDTO.builder()
                            .awardId(result.getAwardId())
                            .awardIndex(result.getSort())
                            .build())
                    .build();
            log.info("随机抽奖完成 strategyId: {} response: {}", requestDTO.getStrategyId(), JSON.toJSONString(response));
            return response;
        } catch (AppException e) {
            log.error("随机抽奖失败 strategyId：{} {}", requestDTO.getStrategyId(), e.getInfo());
            return Response.<RaffleResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();

        }catch (Exception e){
            log.error("随机抽奖失败 strategyId：{}", requestDTO.getStrategyId(), e);
            return Response.<RaffleResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
