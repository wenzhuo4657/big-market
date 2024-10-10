package cn.wenzhuo4657.BigMarket.trigger.http;

import cn.wenzhuo4657.BigMarket.tigger.api.IRaffleService;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleAwardListRequestDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleAwardListResponseDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleRequestDTO;
import cn.wenzhuo4657.BigMarket.tigger.api.dto.RaffleResponseDTO;
import cn.wenzhuo4657.BigMarket.types.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/10
 * @description: 抽奖api
 */
@Slf4j
@RestController
@CrossOrigin("${app.config.cross-orgin}")
@RequestMapping("/api/${app.config.api-version}/raffle/")
public class RaffleController implements IRaffleService {
    @Override
    public Response<Boolean> strategyArmory(Long strategyId) {
        return null;
    }

    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO) {
        return null;
    }
}
