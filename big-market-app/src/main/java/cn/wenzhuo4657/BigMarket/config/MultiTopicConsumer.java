package cn.wenzhuo4657.BigMarket.config;

import cn.wenzhuo4657.BigMarket.trigger.listener.ActivitySkuStockZeroCustomer;
import cn.wenzhuo4657.BigMarket.trigger.listener.CreditAdjustSuccessCustomer;
import cn.wenzhuo4657.BigMarket.trigger.listener.RebateMessageCustomer;
import cn.wenzhuo4657.BigMarket.trigger.listener.SendAwardCustomer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//@Component
//public class MultiTopicConsumer {
//
//
//    @Resource
//    private ActivitySkuStockZeroCustomer activitySkuStockZeroCustomer;
//
//    @Resource
//    private CreditAdjustSuccessCustomer creditAdjustSuccessCustomer;
//    @Resource
//    private RebateMessageCustomer rebateMessageCustomer;
//    @Resource
//    private SendAwardCustomer sendAwardCustomer;
//
//    public MultiTopicConsumer() throws Exception {
//        // 创建消费者实例
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("big-market-app-Customer");
//
//        // 设置 NameServer 地址
//        consumer.setNamesrvAddr("117.72.36.124:9876");
//
//        // 设置消费模式
//        consumer.setMessageModel(MessageModel.CLUSTERING);
//
//        // 订阅多个主题
//        consumer.subscribe("send_rebate", "*");
//        consumer.subscribe("activity_sku_stock_zero", "*");
//        consumer.subscribe("send_award","*");
//        consumer.subscribe("credit_adjust_success","*");
//
//        // 注册消息监听器
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                for (MessageExt msg : msgs) {
//                   switch (msg.getTopic()){
//                       case "send_rebate":sendAwardCustomer.onMessage(msg.getBody().toString());break;
//                       case "activity_sku_stock_zero":activitySkuStockZeroCustomer.onMessage(msg.getBody().toString());break;
//                       case "send_award":sendAwardCustomer.onMessage(msg.getBody().toString());break;
//                       case "credit_adjust_success":creditAdjustSuccessCustomer.onMessage(msg.getBody().toString());break;
//                   }
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//
//        // 启动消费者
//        consumer.start();
//        System.out.println("Consumer started");
//    }
//}