package cn.wenzhuo4657.LuckySphere.config;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class myShardingAlgorith implements StandardShardingAlgorithm<String> {

    private Logger log = LoggerFactory.getLogger(myShardingAlgorith.class);
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
//        todo 如果是表名，则后缀有4个和一个两种形式，但是如果是数据库名则有两种，
//        如果这里一定要根据数据库后缀配置，则可以将后缀写在配置文件中，但是这样过于麻烦，倒不如做类似于hashmod自动分片的随机选择，我只要根据hashcode获取到一个随机制然后对availableTargetNames进行分片即可

        log.info("availableTargetName:{}", availableTargetNames.toString());
        for (String each : availableTargetNames) {

            if (each.endsWith(String.valueOf(shardingValue.getValue().hashCode() % 2))) {
                return each;
            }
        }
        return null;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        log.info("availableTargetName:{}", availableTargetNames.toString());
        Collection<String> result = new HashSet<>(2, 1F);
        for (int i = shardingValue.getValueRange().lowerEndpoint().hashCode(); i <= shardingValue.getValueRange().upperEndpoint().hashCode(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(String.valueOf(i % 2))) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    @Override
    public String getType() {
        return "myAlgorith";
    }
}
