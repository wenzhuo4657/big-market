package cn.wenzhuo4657.BigMarket.domain.activity.service;

import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/17
 * @description:
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity{

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }
}
