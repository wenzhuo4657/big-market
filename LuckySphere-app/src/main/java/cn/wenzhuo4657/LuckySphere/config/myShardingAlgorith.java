package cn.wenzhuo4657.LuckySphere.config;

import com.alibaba.nacos.common.utils.Preconditions;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;


public final class myShardingAlgorith implements StandardShardingAlgorithm<String> {

    private Logger log = LoggerFactory.getLogger(myShardingAlgorith.class);

    private static final String DATABASES_TABLES_RELEVANCE = "sharding-count";


    private Properties props = new Properties();

    @Override
    public void init(Properties props) {
        this.props=props;
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {

        Preconditions.checkArgument(props.containsKey(DATABASES_TABLES_RELEVANCE), "sharding-count cannot be null.");

        Preconditions.checkArgument(!StringUtils.isEmpty(shardingValue.getValue()), "sharding-value cannot be null.");

        int shardingCount = Integer.parseInt(props.getProperty(DATABASES_TABLES_RELEVANCE));

        log.info("availableTargetName:{}", availableTargetNames.toString());

        int index =Math.abs(shardingValue.getValue().hashCode()) %shardingCount;
//        todo 通过hashcode来控制分片，难以控制分布均匀
        String[] array = availableTargetNames.toArray(new String[availableTargetNames.size()]);
        return array[index%array.length];
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        return availableTargetNames;
    }

    @Override
    public String getType() {
        return "SubDatabaseAndSubTable";
    }
}
