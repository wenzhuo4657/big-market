package cn.wenzhuo4657.BigMarket.types.common;

import java.util.HashMap;

public  class Constants {

    public final static String SPLIT = ",";
    public final static String COLON = ":";
    public final static String SPACE = " ";
    public final static String UNDERLINE = "_";
    public static class RedisKey {

        public static String ACTIVITY_KEY = "big_market_activity_key_";
        public static String ACTIVITY_SKU_KEY = "big_market_activity_sku_key_";
        public static String ACTIVITY_COUNT_KEY = "big_market_activity_count_key_";

        /**
         *  @author:wenzhuo4657
            des: 活动sku数量缓存前缀
        */
        public static String ACTIVITY_SKU_COUNT_QUERY_KEY = "activity_sku_count_query_key";

        /**
         *  @author:wenzhuo4657
            des: 活动sku库存缓存前缀
        */
        public static String ACTIVITY_SKU_STOCK_COUNT_KEY = "activity_sku_stock_count_key_";

        /**
         *  @author:wenzhuo4657
            des: 策略实体数据（cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity）的key
        */

        public static String STRATEGY_KEY = "big_market_strategy_key_";


        /**
             *  des:
           *  策略奖品实体的key的key前缀,注意：该字段不在表示某一策略的奖品列表，而是某一策略下的某一奖品实体。
             * */
        public static String STRATEGY_AWARD_KEY = "big_market_strategy_award_key_";
        /**
         *  @author:wenzhuo4657
            des: 策略奖品列表key前缀
        */

        public static String STRATEGY_AWARD_LIST_KEY = "big_market_strategy_award_list_key_";

          /**
             *  des:
           *  策略范围值对应的奖品查询表的key前缀
             * */
        public static String STRATEGY_RATE_TABLE_KEY = "big_market_strategy_rate_table_key_";
          /**
             *  des:范围值的key前缀
             * */
        public static String STRATEGY_RATE_RANGE_KEY = "big_market_strategy_rate_range_key_";

        /**
         *  @author:wenzhuo4657
            des: 规则树key前缀
        */

        public static String RULE_TREE_VO_KEY = "rule_tree_vo_key_";

        /**
         *  @author:wenzhuo4657
            des: 策略奖品库存key前缀
        */
        public static String STRATEGY_AWARD_COUNT_KEY = "strategy_award_count_key_";
        public static String STRATEGY_AWARD_COUNT_QUERY_KEY = "strategy_award_count_query_key";
        public static String STRATEGY_RULE_WEIGHT_KEY = "strategy_rule_weight_key_";
        public static String USER_CREDIT_ACCOUNT_LOCK = "user_credit_account_lock_";
        public static String ACTIVITY_ACCOUNT_UPDATE_LOCK = "activity_account_update_lock_";



        public static  class  RedisKey_ID{
//            该类用于存储缓存存储库表id的key，且必须要注意的，由于并非只有一个活动，所以每一个将其设置统一的初值并不现实，此处key对应的值应尽量为最大的id,
//            在我们尝试缓存这个key时，其他服务不应当使用自增，他的定位应当时程序的初始化，而非会失效的，在程序执行过程中面临并发危险的缓存。

            public static  String raffle_activity_account_id="raffle_activity_account_id";
            public  static  String raffle_activity_account_day_id="raffle_activity_account_day_id";
            public static  String  raffle_activity_account_month_id="raffle_activity_account_month_id";
            public  static  String raffle_activity_order_id="raffle_activity_order_id";
            public  static  String task_id="task_id";
            public  static  String  user_award_record_id="user_award_record_id";
            public  static  String user_behavior_rebate_order_id="user_behavior_rebate_order_000";
            public  static  String  user_credit_account_id="user_credit_account_id";
            public  static  String   user_credit_order_id="user_credit_order_id";
            public  static  String   user_raffle_order_id="user_raffle_order_id";
        }
    }

}
