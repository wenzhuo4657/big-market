package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao;


import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.Award;

import java.util.List;

/**
 * (Award)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 20:54:36
 */


public interface AwardDao {


    List<Award> queryAwardList();

    String queryAwardConfigByAwardId(Integer awardId);

    String queryAwardKeyByAwardId(Integer awardId);
}

