package cn.wenzhuo4657.LuckySphere.domain.activity.service.product;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.entity.SkuProductEntity;
import cn.wenzhuo4657.LuckySphere.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.LuckySphere.domain.activity.service.IRaffleActivitySkuProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/12
 * @description: 服务实现
 */

@Service
@Slf4j
public class RaffleActivitySkuProductService  implements IRaffleActivitySkuProductService {

    @Resource
    private IActivityRepository repository;
    @Override
    public List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId) {
        return repository.querySkuProductEntityListByActivityId(activityId);
    }
}
