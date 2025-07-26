package cn.wenzhuo4657.LuckySphere.config;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.List;

/**
 * 该算法意义是在sharding中配置单节点的算法，他们只需要使用给出的节点即可，不需要进行额外的逻辑
 */

public final class DefaultShardingAlgorith implements StandardShardingAlgorithm<Integer> {

    @Override
    public String getType() {
        return "direct";
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Integer> shardingValue) {
        if (availableTargetNames.size() !=0){
            throw new RuntimeException("availableTargetNames.size() !=0    意外的用法");
        }
        return availableTargetNames;
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {

        if (availableTargetNames.size() !=0){
            throw new RuntimeException("availableTargetNames.size() !=0    意外的用法");
        }
        return availableTargetNames.iterator().next();
    }
}
