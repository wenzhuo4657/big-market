package cn.wenzhuo4657.BigMarket.domain.credit.service;

import cn.wenzhuo4657.BigMarket.domain.credit.model.aggregate.TradeAggregate;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.TradeEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.repository.ICreditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description: 积分调额服务
 */
@Slf4j
@Service
public class CreditAdjustService implements  ICreditAdjustService{
    @Resource
    private ICreditRepository creditRepository;
    @Override
    public String createOrder(TradeEntity tradeEntity) {
        log.info("增加账户积分额度开始 userId:{} tradeName:{} amount:{}", tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getAmount());
        CreditOrderEntity creditOrderEntity=
                TradeAggregate.createCreditOrderEntity(tradeEntity.getUserId()
                                                            ,tradeEntity.getTradeName(),tradeEntity.getTradeType(),tradeEntity.getAmount(),tradeEntity.getOutBusinessNo());
        CreditAccountEntity creditAccountEntity=TradeAggregate.createCreditAccountEntity(tradeEntity.getUserId()
                                                            ,tradeEntity.getAmount());

        TradeAggregate   tradeAggregate=TradeAggregate.builder()
                .userId(tradeEntity.getUserId())
                .creditOrderEntity(creditOrderEntity)
                .creditAccountEntity(creditAccountEntity).build();
        creditRepository.saveUserCreditTradeOrder(tradeAggregate);
        log.info("增加账户积分额度完成 userId:{} orderId:{}", tradeEntity.getUserId(), creditOrderEntity.getOrderId());

        return creditOrderEntity.getOrderId();
    }
}
