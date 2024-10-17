package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 抽奖活动单(RaffleActivityOrder)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-17 09:12:07
 */
public interface RaffleActivityOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RaffleActivityOrder queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param raffleActivityOrder000 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<RaffleActivityOrder> queryAllByLimit(RaffleActivityOrder raffleActivityOrder000, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param raffleActivityOrder000 查询条件
     * @return 总行数
     */
    long count(RaffleActivityOrder raffleActivityOrder000);

    /**
     * 新增数据
     *
     * @param raffleActivityOrder000 实例对象
     * @return 影响行数
     */
    int insert(RaffleActivityOrder raffleActivityOrder000);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivityOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RaffleActivityOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivityOrder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RaffleActivityOrder> entities);

    /**
     * 修改数据
     *
     * @param raffleActivityOrder000 实例对象
     * @return 影响行数
     */
    int update(RaffleActivityOrder raffleActivityOrder000);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

