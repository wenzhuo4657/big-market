package cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.factory;

import cn.wenzhuo4657.BigMarket.domain.activity.service.quota.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/18
 * @description: 活动责任链工厂
 */

@Service
public class DefaultActivityChainFactory {
    private  final IActionChain actionChain;



    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        this.actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.getCode()));
    }

    public IActionChain openActionChain(){
        return  this.actionChain;
    }


    /**
     *  @author:wenzhuo4657
        des: 活动类型描述
    */
    @Getter
    @AllArgsConstructor
    public enum ActionModel {

        activity_base_action("activity_base_action", "活动的库存、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存"),
        ;

        private final String code;
        private final String info;

    }
}
