package cn.wenzhuo4657.BigMarket.types.common;

public  class Constants {

    public final static String SPLIT = ",";
    public final static String COLON = ":";
    public final static String SPACE = " ";
    public final static String UNDERLINE = "_";
    public static class RedisKey {


        /**
         *  @author:wenzhuo4657
            des: 策略实体数据（cn.wenzhuo4657.BigMarket.domain.strategy.model.entity.StrategyEntity）的key
        */

        public static String STRATEGY_KEY = "big_market_strategy_key_";


        /**
             *  des:
           *  策略奖品实体集合的key的key前缀
             * */
        public static String STRATEGY_AWARD_KEY = "big_market_strategy_award_key_";

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

        /**
         *  @author:wenzhuo4657
            des:
        */
        public static String STRATEGY_AWARD_COUNT_QUERY_KEY = "strategy_award_count_query_key";
    }



}
