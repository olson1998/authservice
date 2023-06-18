package com.olson1998.authdata.application.datasource;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

@Slf4j
public class TenantRollingDataSource implements DataSource {

    private static final ThreadLocal<DataSource> CURRENT_THREAD_TENANT_DATASOURCE = new ThreadLocal<>();

    private static final ConcurrentMap<String, DataSource> TENANTS_DATA_SOURCES =
            new ConcurrentHashMap<>();

    public static void setCurrentThreadTenantDatasource(String tid){
        var ds = TENANTS_DATA_SOURCES.get(tid);
        CURRENT_THREAD_TENANT_DATASOURCE.set(ds);
        log.debug("Setting local thread data source of tenant: '{}', instance: '{}'", tid, ds.getClass().getName());
    }

    public static void appendDataSource(String tid, DataSource dataSource){
        TENANTS_DATA_SOURCES.put(tid, dataSource);
    }

    public static DataSource getTenantDataSource(String tid){
        return Optional.ofNullable(TENANTS_DATA_SOURCES.get(tid))
                .orElseThrow();
    }

    public static void clean(){
        CURRENT_THREAD_TENANT_DATASOURCE.remove();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        CURRENT_THREAD_TENANT_DATASOURCE.get().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        CURRENT_THREAD_TENANT_DATASOURCE.get().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().getLoginTimeout();
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().createConnectionBuilder();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().getParentLogger();
    }

    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().createShardingKeyBuilder();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return CURRENT_THREAD_TENANT_DATASOURCE.get().isWrapperFor(iface);
    }
}
