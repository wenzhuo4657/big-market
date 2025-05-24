package cn.wenzhuo4657.BigMarket.querys.repository;

import cn.wenzhuo4657.BigMarket.querys.entity.UserAwardRecordVO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUserAwardRecordRepository extends PagingAndSortingRepository<UserAwardRecordVO, String> {
}
