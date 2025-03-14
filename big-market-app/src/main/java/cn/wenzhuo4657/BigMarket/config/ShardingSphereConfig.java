package cn.wenzhuo4657.BigMarket.config;

import org.apache.shardingsphere.infra.binder.statement.SQLStatementContext;
import org.apache.shardingsphere.infra.config.props.ConfigurationProperties;
import org.apache.shardingsphere.infra.database.type.DatabaseType;
import org.apache.shardingsphere.infra.merge.engine.merger.ResultMerger;
import org.apache.shardingsphere.infra.merge.engine.merger.ResultMergerEngine;
import org.apache.shardingsphere.infra.rule.ShardingSphereRule;

/**
 * @author: wenzhuo4657
 * @date: 2025/3/14
 * @description:
 */
public class ShardingSphereConfig implements ResultMergerEngine {
    @Override
    public ResultMerger newInstance(String schemaName, DatabaseType databaseType, ShardingSphereRule rule, ConfigurationProperties props, SQLStatementContext sqlStatementContext) {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Class getTypeClass() {
        return null;
    }
}
