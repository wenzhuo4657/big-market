package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivitySku;
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

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RaffleActivitySku queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param raffleActivitySku 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<RaffleActivitySku> queryAllByLimit(RaffleActivitySku raffleActivitySku, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param raffleActivitySku 查询条件
     * @return 总行数
     */
    long count(RaffleActivitySku raffleActivitySku);

    /**
     * 新增数据
     *
     * @param raffleActivitySku 实例对象
     * @return 影响行数
     */
    int insert(RaffleActivitySku raffleActivitySku);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivitySku> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RaffleActivitySku> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivitySku> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RaffleActivitySku> entities);

    /**
     * 修改数据
     *
     * @param raffleActivitySku 实例对象
     * @return 影响行数
     */
    int update(RaffleActivitySku raffleActivitySku);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

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
}

