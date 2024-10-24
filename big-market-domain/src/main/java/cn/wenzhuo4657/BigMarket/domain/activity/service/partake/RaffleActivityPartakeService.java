package cn.wenzhuo4657.BigMarket.domain.activity.service.partake;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.UserRaffleOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/24
 * @description:
 */
@Service
public class RaffleActivityPartakeService extends AbstractRaffleActivityPartake{
    private final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");
    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");

    public RaffleActivityPartakeService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

    @Override
    protected CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate) {

        return null;
    }

    @Override
    protected UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate) {
        return null;
    }
}
