package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;

import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 抽奖活动账户表(RaffleActivityAccount)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-17 09:11:48
 */
public interface RaffleActivityAccountDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RaffleActivityAccount queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param raffleActivityAccount 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<RaffleActivityAccount> queryAllByLimit(RaffleActivityAccount raffleActivityAccount, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param raffleActivityAccount 查询条件
     * @return 总行数
     */
    long count(RaffleActivityAccount raffleActivityAccount);

    /**
     * 新增数据
     *
     * @param raffleActivityAccount 实例对象
     * @return 影响行数
     */
    int insert(RaffleActivityAccount raffleActivityAccount);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivityAccount> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RaffleActivityAccount> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RaffleActivityAccount> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RaffleActivityAccount> entities);

    /**
     * 修改数据
     *
     * @param raffleActivityAccount 实例对象
     * @return 影响行数
     */
    int update(RaffleActivityAccount raffleActivityAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

