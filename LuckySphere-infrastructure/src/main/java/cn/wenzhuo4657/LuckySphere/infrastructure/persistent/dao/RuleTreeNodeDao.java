package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;

import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (RuleTreeNode)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-05 15:44:36
 */
public interface RuleTreeNodeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RuleTreeNode queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param ruleTreeNode 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<RuleTreeNode> queryAllByLimit(RuleTreeNode ruleTreeNode, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param ruleTreeNode 查询条件
     * @return 总行数
     */
    long count(RuleTreeNode ruleTreeNode);

    /**
     * 新增数据
     *
     * @param ruleTreeNode 实例对象
     * @return 影响行数
     */
    int insert(RuleTreeNode ruleTreeNode);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RuleTreeNode> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RuleTreeNode> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RuleTreeNode> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RuleTreeNode> entities);

    /**
     * 修改数据
     *
     * @param ruleTreeNode 实例对象
     * @return 影响行数
     */
    int update(RuleTreeNode ruleTreeNode);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(@Param("treeId") String treeId);

    List<RuleTreeNode> queryRuleLocks(@Param("array") String[] treeIds);
}

