package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.BugleCaller;
import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityAccountMonth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 抽奖活动账户表-月次数(RaffleActivityAccountMonth)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-24 08:07:43
 */
@Mapper
public interface RaffleActivityAccountMonthDao extends BugleCaller {



    RaffleActivityAccountMonth queryActivityAccountMonthByUserId(RaffleActivityAccountMonth raffleActivityAccountMonthReq);

    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonth build);

    void insertActivityAccountMonth(RaffleActivityAccountMonth build);

    void addAccountQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);

    @Override
    List<Long> getId();
}

