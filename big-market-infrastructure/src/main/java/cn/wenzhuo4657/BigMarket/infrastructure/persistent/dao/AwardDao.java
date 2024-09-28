package cn.wenzhuo4657.BigMarket.infrastructure.persistent.dao;


import cn.wenzhuo4657.BigMarket.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Award)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-18 20:54:36
 */


public interface AwardDao {


    List<Award> queryAwardList();
}

