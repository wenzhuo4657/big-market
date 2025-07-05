package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (RaffleActivitySku)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-17 08:43:07
 */
public interface RaffleActivitySkuDao {


    RaffleActivitySku queryBySku(Long sku);

    /**
     *  @author:wenzhuo4657
        des: 库存-1，并更新updata-time字段。
    */
    void updateActivitySkuStock(Long sku);

    /**
     *  @author:wenzhuo4657
        des: 清空库存
    */
    void clearActivitySkuStock(Long sku);

    List<RaffleActivitySku> queryActivitySkuListByActivityId(Long activityId);
}

