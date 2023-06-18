package com.olson1998.authdata.domain.service.processing.datasource;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.olson1998.authdata.domain.model.exception.processing.NoPortSpecified;
import com.olson1998.authdata.domain.model.exception.processing.NoTenantDataSourceUserAvailable;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import com.olson1998.authdata.domain.port.processing.datasource.SqlDataSourceFactory;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mariadb.jdbc.MariaDbDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

@Slf4j
public class TenantSqlDataSourceFactory implements SqlDataSourceFactory {

    private static final String MARIA_DB_PROTOCOL = "jdbc:mariadb://";

    @Override
    public DataSource fabricate(@NonNull TenantDataSource tenantDataSource) {
        var db = tenantDataSource.getSqlDataSourceType();
        if(db.isMsSql()){
            return fabricateMsSqlDs(tenantDataSource);
        } else if (db.isMariaDb()) {
            return fabricateMariaDbDs(tenantDataSource);
        } else if (db.isPostgres()) {
            return fabricatePostgresDs(tenantDataSource);
        }else {
            throw new UnknownError("unknown type of tenant data source");
        }
    }

    @SneakyThrows
    private MariaDbDataSource fabricateMariaDbDs(TenantDataSource tenantDataSource){
        var mariaDbDs = new MariaDbDataSource();
        var user = getAny(tenantDataSource);
        var db = tenantDataSource.getDatabase();
        var host = tenantDataSource.getHost();
        var port = tenantDataSource.getPort()
                        .orElseThrow(()-> new NoPortSpecified(log, mariaDbDs.getClass(), tenantDataSource.getTid()));
        var url = new StringBuilder(MARIA_DB_PROTOCOL)
                .append(host)
                .append(':')
                .append(port)
                .append('/')
                .append(db)
                .toString();
        mariaDbDs.setUrl(url);
        mariaDbDs.setUser(user.getUsername());
        mariaDbDs.setPassword(user.getPassword());
        if(tenantDataSource.getLoginTimeout().isPresent()){
            mariaDbDs.setLoginTimeout(tenantDataSource.getLoginTimeout().get());
        }
        return mariaDbDs;
    }

    private SQLServerDataSource fabricateMsSqlDs(TenantDataSource tenantDataSource){
        var msSqlDs = new SQLServerDataSource();
        var user = getAny(tenantDataSource);
        msSqlDs.setServerName(tenantDataSource.getHost());
        msSqlDs.setDatabaseName(tenantDataSource.getDatabase());
        if(tenantDataSource.getPort().isPresent()){
            msSqlDs.setPortNumber(tenantDataSource.getPort().get());
        }
        msSqlDs.setUser(user.getUsername());
        msSqlDs.setPassword(user.getPassword());
        msSqlDs.setDescription("data source of tenant: "+ tenantDataSource.getTid());
        if(tenantDataSource.getLoginTimeout().isPresent()){
            msSqlDs.setLoginTimeout(tenantDataSource.getLoginTimeout().get());
        }
        return msSqlDs;
    }

    private PGSimpleDataSource fabricatePostgresDs(TenantDataSource tenantDataSource){
        var pgDs = new PGSimpleDataSource();
        var user = getAny(tenantDataSource);
        var port = tenantDataSource.getPort()
                .orElseThrow(()-> new NoPortSpecified(log, pgDs.getClass(), tenantDataSource.getTid()));
        var url = String.format("%s:%s", tenantDataSource.getHost(), port);
        pgDs.setUrl(url);
        pgDs.setUser(user.getUsername());
        pgDs.setPassword(user.getPassword());
        if(tenantDataSource.getLoginTimeout().isPresent()){
            pgDs.setLoginTimeout(tenantDataSource.getLoginTimeout().get());
        }
        return pgDs;
    }

    private TenantDataSourceUser getAny(TenantDataSource tenantDataSource){
        return tenantDataSource.getTenantDataSourceUsers().stream()
                .findAny()
                .orElseThrow(()-> new NoTenantDataSourceUserAvailable(log, "No tenant user available"));
    }
}
