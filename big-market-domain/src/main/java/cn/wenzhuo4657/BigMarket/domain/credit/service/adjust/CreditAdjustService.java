package cn.wenzhuo4657.BigMarket.domain.credit.service.adjust;

import cn.wenzhuo4657.BigMarket.domain.credit.event.CreditAdjustSuccessMessageEvent;
import cn.wenzhuo4657.BigMarket.domain.credit.model.aggregate.TradeAggregate;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditAccountEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.CreditOrderEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.TaskEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.entity.TradeEntity;
import cn.wenzhuo4657.BigMarket.domain.credit.model.valobj.TradeTypeVO;
import cn.wenzhuo4657.BigMarket.domain.credit.repository.ICreditRepository;
import cn.wenzhuo4657.BigMarket.domain.credit.service.ICreditAdjustService;
import cn.wenzhuo4657.BigMarket.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/9
 * @description: 积分调额服务
 */
@Slf4j
@Service
public class CreditAdjustService implements ICreditAdjustService {
    @Resource
    private ICreditRepository creditRepository;

    @Resource
    private CreditAdjustSuccessMessageEvent creditAdjustSuccessMessageEvent;

    @Override
    public String createOrder(TradeEntity tradeEntity) {
        log.info("sku商品交易开始 userId:{} tradeName:{} amount:{}", tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getAmount());
        CreditOrderEntity creditOrderEntity =
                TradeAggregate.createCreditOrderEntity(tradeEntity.getUserId()
                        , tradeEntity.getTradeName(), tradeEntity.getTradeType(), tradeEntity.getAmount(), tradeEntity.getOutBusinessNo());
        CreditAccountEntity creditAccountEntity = creditRepository.queryUserCreditAccount(tradeEntity.getUserId());


        CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage creditAdjustSuccessMessage = new CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage();
        creditAdjustSuccessMessage.setUserId(tradeEntity.getUserId());
        creditAdjustSuccessMessage.setOrderId(creditOrderEntity.getOrderId());
        creditAdjustSuccessMessage.setAmount(tradeEntity.getAmount());
        creditAdjustSuccessMessage.setOutBusinessNo(tradeEntity.getOutBusinessNo());

        BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> creditAdjustSuccessMessageEventMessage = creditAdjustSuccessMessageEvent.buildEventMessage(creditAdjustSuccessMessage);

        TaskEntity taskEntity = TradeAggregate.createTaskEntity
                (tradeEntity.getUserId(),
                        creditAdjustSuccessMessageEvent.topic(),
                        creditAdjustSuccessMessageEventMessage.getId(),
                        creditAdjustSuccessMessageEventMessage);

        TradeAggregate tradeAggregate = TradeAggregate.builder()
                .userId(tradeEntity.getUserId())
                .creditAccountEntity(creditAccountEntity)
                .creditOrderEntity(creditOrderEntity)
                .taskEntity(taskEntity)
                .build();
        creditRepository.saveUserCreditTradeOrder(tradeAggregate);
        log.info("增加账户积分额度完成 userId:{} orderId:{}", tradeEntity.getUserId(), creditOrderEntity.getOrderId());
        return creditOrderEntity.getOrderId();
    }

    @Override
    public void saveIntegralRebateOrder(TradeEntity tradeEntity) {
        log.info("积分订单交易开始 userId:{} tradeName:{} amount:{}", tradeEntity.getUserId(), tradeEntity.getTradeName());

        BigDecimal amount = tradeEntity.getAmount();
        BigDecimal multiply;
        if (TradeTypeVO.REVERSE.getCode().equals(tradeEntity.getTradeType().getCode())){
            multiply=amount.multiply(new BigDecimal("-1"));
        }else {
            multiply=amount;
        }
        creditRepository.updateCreditAccount(multiply,tradeEntity.getUserId());

    }

    @Override
    public CreditAccountEntity queryUserCreditAccount(String userId) {

        return creditRepository.queryUserCreditAccount(userId);
    }
}
