package cn.wenzhuo4657.BigMarket.querys.repository;

import cn.wenzhuo4657.BigMarket.querys.entity.RaffleActivityOrderVO;
import cn.wenzhuo4657.BigMarket.querys.entity.UserRaffleOrderVO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IRaffleActivityOrderRepository extends PagingAndSortingRepository<RaffleActivityOrderVO, String> {
}
