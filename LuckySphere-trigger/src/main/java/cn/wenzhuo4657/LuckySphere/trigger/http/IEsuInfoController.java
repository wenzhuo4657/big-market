package cn.wenzhuo4657.LuckySphere.trigger.http;


import cn.wenzhuo4657.LuckySphere.querys.entity.UserRaffleOrderVO;
import cn.wenzhuo4657.LuckySphere.querys.repository.IRaffleActivityOrderRepository;
import cn.wenzhuo4657.LuckySphere.querys.repository.IUserAwardRecordRepository;
import cn.wenzhuo4657.LuckySphere.querys.repository.IUserBehaviorRebateOrderRepository;
import cn.wenzhuo4657.LuckySphere.querys.repository.IUserRaffleOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务数据的展示层，数据来源： elasticsearch
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/esu/query/")
public class IEsuInfoController {

// todo 这里的接口都访问不到
    @Autowired
    private IUserBehaviorRebateOrderRepository userBehaviorRebateOrderRepository;


    @Autowired
    private IRaffleActivityOrderRepository raffleActivityOrderRepository;

    @Autowired
    private IUserAwardRecordRepository userAwardRecordRepository;

    @Autowired
    private IUserRaffleOrderRepository  userRaffleOrderRepository;


//    todo  这里要支持分页查询、动态查询
    @RequestMapping("query_user_raffle_order")
    public String queryUserRaffleOrder()
    {

        List<Sort.Order>  orders=new ArrayList<>();
        Sort sort  = Sort.by(orders);
        Iterable<UserRaffleOrderVO> all = userRaffleOrderRepository.findAll(sort);
        return  null;
    }

    @RequestMapping("query_user_award_record")
    public String queryUserAwardRecord()
        {
            return "query_user_award_record";
        }

        @RequestMapping("query_user_behavior_rebate_order")
        public String queryUserBehaviorRebateOrder()
        {
            return "query_user_behavior_rebate_order";
        }
        @RequestMapping("query_user_behavior_rebate_order_detail")
        public String queryUserBehaviorRebateOrderDetail()
        {
            return "query_user_behavior_rebate_order_detail";
        }
}
