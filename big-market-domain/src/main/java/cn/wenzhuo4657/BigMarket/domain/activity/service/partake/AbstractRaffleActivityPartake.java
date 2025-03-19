package cn.wenzhuo4657.BigMarket.domain.activity.service.partake;

import cn.wenzhuo4657.BigMarket.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.ActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.PartakeRaffleActivityEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.entity.UserRaffleOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.activity.model.valobj.ActivityStateVO;
import cn.wenzhuo4657.BigMarket.domain.activity.repository.IActivityRepository;
import cn.wenzhuo4657.BigMarket.domain.activity.service.IRaffleActivityPartakeService;
import cn.wenzhuo4657.BigMarket.types.enums.ResponseCode;
import cn.wenzhuo4657.BigMarket.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/24
 * @description: 定义模板流程
 */
@Slf4j
public abstract class AbstractRaffleActivityPartake implements IRaffleActivityPartakeService {
    protected final IActivityRepository activityRepository;

    public AbstractRaffleActivityPartake(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     *  @author:wenzhuo4657
        des: 创建订单的模板方法，对于其中的两个抽象方法，
    doFilterAccount，buildUserRaffleOrder都用于构建聚合根createPartakeOrderAggregate，
     其中前者的作用时填充账户额度的有关信息，并且保证账户额度不为空，后者时用于创建抽奖订单。总之这两个方法的目标时创建一个有效可以消费的聚合根。
     当然，在真正消费时使用sql语句依旧会进行判断是否可以正常消费，（会进行行级锁定，有相关的索引字段。）
    */
    @Override
    public UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        Date currentDate=new Date();
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);

        if (!ActivityStateVO.open.equals(activityEntity.getState())){
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(),ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        if (activityEntity.getBeginDateTime().after(currentDate) || activityEntity.getEndDateTime().before(currentDate)) {
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }

        UserRaffleOrderEntity userRaffleOrderEntity = activityRepository.queryNoUsedRaffleOrder(partakeRaffleActivityEntity);
        if (null != userRaffleOrderEntity) {
            return userRaffleOrderEntity;
        }
        log.info("创建参与活动订单 userId:{} activityId:{} userRaffleOrderEntity:{}", userId, activityId, JSON.toJSONString(userRaffleOrderEntity));

//        不存在活动订单，尝试从活动额度中扣去
        CreatePartakeOrderAggregate createPartakeOrderAggregate = this.doFilterAccount(userId, activityId, currentDate);

        UserRaffleOrderEntity userRaffleOrder = this.buildUserRaffleOrder(userId, activityId, currentDate);

        createPartakeOrderAggregate.setUserRaffleOrderEntity(userRaffleOrder);

        activityRepository.saveCreatePartakeOrderAggregate(createPartakeOrderAggregate);
        return userRaffleOrder;


    }

    @Override
    public UserRaffleOrderEntity createOrder(String userId, Long activityId) {
        return createOrder(PartakeRaffleActivityEntity.builder()
                .userId(userId)
                .activityId(activityId)
                .build());
    }

    /**
     *  @author:wenzhuo4657
        des:  创建参与活动订单，
    且注意，对于数据库而言，必须存在总账户，但是对于月账户和日账户，这一点可以从这个聚合根的字段看出，具有月账户和日账户是否存在的字段，但是没有总账户是否存在的字段。
    因此，该方法创建的聚合根数据是可以信任的，在activityRepository.saveCreatePartakeOrderAggregate()中使用jdbc事务时，会根据是否存在响应账户对mysql中响应表数据进行修改。

    */
    protected abstract CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate);

    protected abstract UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate);
}
