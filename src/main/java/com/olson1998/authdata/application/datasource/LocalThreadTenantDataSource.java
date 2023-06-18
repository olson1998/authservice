package com.olson1998.authdata.application.datasource;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import static java.util.Map.entry;

@Slf4j

@Component(value = "localThreadTenantDataSource")
@RequiredArgsConstructor
public class LocalThreadTenantDataSource implements DataSource {

    private static final String SELF_TENANT = "SELF_TENANT";

    private static final JdbcDataSource IN_MEMORY_DATA_SOURCE = selfTenantHost();

    private static final ThreadLocal<DataSource> CURRENT_THREAD_TENANT_DATASOURCE = new ThreadLocal<>();

    private static final ConcurrentMap<String, DataSource> TENANTS_DATA_SOURCES =
            new ConcurrentHashMap<>(Map.ofEntries(
                    entry(SELF_TENANT, IN_MEMORY_DATA_SOURCE)
            ));

    private final SpringLiquibase springLiquibase;

    public void setCurrentThreadTenantDatasource(String tid){
        var ds = TENANTS_DATA_SOURCES.get(tid);
        CURRENT_THREAD_TENANT_DATASOURCE.set(ds);
        log.debug("Setting local thread data source of tenant: '{}', instance: '{}'", tid, ds.getClass().getName());
    }

    @SneakyThrows
    public void appendDataSource(String tid, DataSource dataSource){
        springLiquibase.setShouldRun(true);
        springLiquibase.setDefaultSchema(null);
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
}
