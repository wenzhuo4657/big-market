package cn.wenzhuo4657.BigMarket.trigger.http;

import cn.wenzhuo4657.BigMarket.tigger.api.IDCCService;
import cn.wenzhuo4657.BigMarket.tigger.api.reponse.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/16
 * @description:
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/dcc/")
public class DCCController implements IDCCService {
    @Override
    public Response<Boolean> updateConfig(String key, String value) {
        return null;
    }
}
