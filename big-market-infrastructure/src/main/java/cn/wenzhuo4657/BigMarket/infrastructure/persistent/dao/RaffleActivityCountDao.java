package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 抽奖活动次数配置表(RaffleActivityCount)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-17 08:42:56
 */
public interface RaffleActivityCountDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RaffleActivityCount queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param raffleActivityCount 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<RaffleActivityCount> queryAllByLimit(RaffleActivityCount raffleActivityCount, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param raffleActivityCount 查询条件
     * @return 总行数
     */
    long count(RaffleActivityCount raffleActivityCount);

    /**
     * 新增数据
     *
     * @param raffleActivityCount 实例对象
     * @return 影响行数
     */
    int insert(RaffleActivityCount raffleActivityCount);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivityCount> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RaffleActivityCount> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivityCount> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RaffleActivityCount> entities);

    /**
     * 修改数据
     *
     * @param raffleActivityCount 实例对象
     * @return 影响行数
     */
    int update(RaffleActivityCount raffleActivityCount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);
}

