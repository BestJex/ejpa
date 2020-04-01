package vip.efactory.ejpa.config.tenant.ds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import vip.efactory.ejpa.base.entity.TenantEntity;
import vip.efactory.ejpa.config.tenant.id.TenantConstants;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 租户数据源提供者
 */
@Slf4j
@AllArgsConstructor
public class TenantDataSourceProvider {
    // 使用一个map来存储我们租户和对应的数据源，租户和数据源的信息就是从我们的tenant表中读出来
    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    /**
     * 初始化默认数据源，假如我们的访问信息里面没有指定tenantId，就使用默认数据源。
     * 在我这里默认数据源是cloud_config，实际上你可以指向你们的公共信息的库，或者拦截这个操作返回错误。
     */
//    @Autowired
//    @PostConstruct
//    private void initDefaultDataSource(DataSourceProperties defaultDataSource) {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.url(defaultDataSource.getUrl());
//        dataSourceBuilder.username(defaultDataSource.getUsername());
//        dataSourceBuilder.password(defaultDataSource.getPassword());
//        dataSourceBuilder.driverClassName(defaultDataSource.getDriverClassName());
//        dataSourceMap.put(TenantConstants.DEFAULT_TENANT_ID.toString(), dataSourceBuilder.build());
//    }

    // 根据传进来的tenantId决定返回的数据源
    public static DataSource getTenantDataSource(String tenantId) {
        if (dataSourceMap.containsKey(tenantId)) {
            log.info("Get Tenant {} DataSource", tenantId);
            return dataSourceMap.get(tenantId);
        } else {
            log.info("Get Default Tenant DataSource.");
            return dataSourceMap.get(TenantConstants.DEFAULT_TENANT_ID.toString());
        }
    }

    // 初始化的时候用于添加数据源的方法
    public static void addDataSource(TenantEntity tenantEntity) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(tenantEntity.getJdbcUrl());
        dataSourceBuilder.username(tenantEntity.getDbUsername());
        dataSourceBuilder.password(tenantEntity.getDbPassword());
        dataSourceBuilder.driverClassName(tenantEntity.getDriverClassName());
        dataSourceMap.put(tenantEntity.getId().toString(), dataSourceBuilder.build());
    }

    // 根据传进来的tenantId来删除指定的数据源
    public static void removeDataSource(String tenantId) {
        if (dataSourceMap.containsKey(tenantId)) {
            log.info("Remove Tenant {} DataSource", tenantId);
            dataSourceMap.remove(tenantId);
        }
    }

    // 刷新指定租户的数据源
    public static void refreshDataSource(TenantEntity tenantEntity) {
        log.info("Refresh Tenant {} DataSource", tenantEntity.getId());
        addDataSource(tenantEntity);
    }
}
