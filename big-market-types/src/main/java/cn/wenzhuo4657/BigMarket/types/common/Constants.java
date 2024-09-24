package cn.wenzhuo4657.BigMarket.types.common;

public  class Constants {

    public final static String SPLIT = ",";
    public static class RedisKey {

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
    }

}
