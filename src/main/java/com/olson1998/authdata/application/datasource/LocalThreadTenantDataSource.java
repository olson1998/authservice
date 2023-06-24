package com.olson1998.authdata.application.datasource;

import com.olson1998.authdata.application.datasource.properties.DataSourceChangelogProps;
import com.olson1998.authdata.application.security.config.LocalServiceInstanceSign;
import com.olson1998.authdata.domain.port.processing.datasource.TenantSqlDataSourceRepository;
import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

@Slf4j

@Component(value = "localThreadTenantDataSource")
public class LocalThreadTenantDataSource implements DataSource {

    private static final String SELF_TENANT = "MYSELF";

    private static final ThreadLocal<DataSource> CURRENT_THREAD_TENANT_DATASOURCE = new ThreadLocal<>();

    private static final ConcurrentMap<String, DataSource> TENANTS_DATA_SOURCES =
            new ConcurrentHashMap<>();

    private final String tenantDataSourceChangelog;

    private final SpringLiquibase springLiquibase;

    private final LocalServiceInstanceSign localServiceInstanceSign;

    private final TenantSqlDataSourceRepository tenantSqlDataSourceRepository;

    public void setCurrentThreadTenantDatasource(String tid){
        var optionalDs = Optional.ofNullable(TENANTS_DATA_SOURCES.get(tid));
        DataSource ds = null;
        if(optionalDs.isPresent()){
            ds = optionalDs.get();
        }else {
            ds = tenantSqlDataSourceRepository.getForTenant(tid);
            appendDataSource(tid, ds);
        }
        CURRENT_THREAD_TENANT_DATASOURCE.set(ds);
        log.debug("Setting local thread data source of tenant: '{}', instance: '{}'", tid, ds.getClass().getName());
    }

    @SneakyThrows
    public void appendDataSource(String tid, DataSource dataSource){
        log.debug("Appending new data source of tenant: '{}'", tid);
        var liquiParams = new HashMap<String, String>();
        liquiParams.put("tenant.id", tid);
        liquiParams.put("service.ip", localServiceInstanceSign.getValue());
        springLiquibase.setContexts("TENANT:"+tid);
        springLiquibase.setChangeLog(tenantDataSourceChangelog);
        springLiquibase.setShouldRun(true);
        springLiquibase.setDefaultSchema(null);
        springLiquibase.setChangeLogParameters(liquiParams);
        springLiquibase.setDataSource(dataSource);
        springLiquibase.afterPropertiesSet();
        springLiquibase.setShouldRun(false);
        TENANTS_DATA_SOURCES.put(tid, dataSource);
    }

    public void clean(){
        CURRENT_THREAD_TENANT_DATASOURCE.remove();
    }

    public DataSource getCurrentThreadTenantDataSource(){
        return Optional.ofNullable(CURRENT_THREAD_TENANT_DATASOURCE.get())
                .orElse(TENANTS_DATA_SOURCES.get(SELF_TENANT));
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getCurrentThreadTenantDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getCurrentThreadTenantDataSource().getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getCurrentThreadTenantDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getCurrentThreadTenantDataSource().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getCurrentThreadTenantDataSource().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getCurrentThreadTenantDataSource().getLoginTimeout();
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return getCurrentThreadTenantDataSource().createConnectionBuilder();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getCurrentThreadTenantDataSource().getParentLogger();
    }

    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return getCurrentThreadTenantDataSource().createShardingKeyBuilder();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getCurrentThreadTenantDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getCurrentThreadTenantDataSource().isWrapperFor(iface);
    }

    private static JdbcDataSource selfTenantHost(){
        var ds = new JdbcDataSource();
        ds.setUser("user");
        ds.setPassword("pass");
        ds.setUrl("jdbc:h2:mem:self-tenant");
        return ds;
    }

    public LocalThreadTenantDataSource(DataSourceChangelogProps dataSourceChangelogProps,
                                       SpringLiquibase springLiquibase,
                                       TenantSqlDataSourceRepository tenantSqlDataSourceRepository,
                                       LocalServiceInstanceSign localServiceInstanceSign) {
        this.springLiquibase = springLiquibase;
        this.tenantDataSourceChangelog = dataSourceChangelogProps.getTenantDataBase().getChangeLog();
        this.tenantSqlDataSourceRepository = tenantSqlDataSourceRepository;
        this.localServiceInstanceSign = localServiceInstanceSign;
        this.appendDataSource(SELF_TENANT, selfTenantHost());
    }
}
