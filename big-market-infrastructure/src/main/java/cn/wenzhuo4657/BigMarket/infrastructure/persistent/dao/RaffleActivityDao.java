package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 抽奖活动表(RaffleActivity)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-15 19:17:16
 */
public interface RaffleActivityDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RaffleActivity queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param raffleActivity 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<RaffleActivity> queryAllByLimit(RaffleActivity raffleActivity, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param raffleActivity 查询条件
     * @return 总行数
     */
    long count(RaffleActivity raffleActivity);

    /**
     * 新增数据
     *
     * @param raffleActivity 实例对象
     * @return 影响行数
     */
    int insert(RaffleActivity raffleActivity);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivity> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RaffleActivity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivity> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RaffleActivity> entities);

    /**
     * 修改数据
     *
     * @param raffleActivity 实例对象
     * @return 影响行数
     */
    int update(RaffleActivity raffleActivity);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

