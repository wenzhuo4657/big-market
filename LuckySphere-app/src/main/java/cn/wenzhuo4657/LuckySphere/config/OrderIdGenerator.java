package cn.wenzhuo4657.LuckySphere.config;


import cn.wenzhuo4657.LuckySphere.types.utils.RandomOrderIdUtils;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;

public class OrderIdGenerator implements KeyGenerateAlgorithm {

    @Override
    public Comparable<?> generateKey() {
        return RandomOrderIdUtils.getOrderId();
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return "my_Algorithm";
    }
}
