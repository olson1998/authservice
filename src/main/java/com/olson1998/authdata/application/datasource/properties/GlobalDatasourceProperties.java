package com.olson1998.authdata.application.datasource.properties;

import com.olson1998.authdata.application.datasource.entity.global.values.SqlDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import com.olson1998.authdata.domain.port.data.utils.SqlDataSourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.datasource.global")
public class GlobalDatasourceProperties implements TenantDataSource{

    private SqlDataSource type;

    private String description = "global datasource";

    private User user = new User();

    private String host;

    private Integer port;

    private String database;

    private String schema;

    private boolean trustCertificate = true;

    private Integer loginTimeout = 30;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class User implements TenantDataSourceUser {

        private String username;

        private String password;

        @Override
        public Long getDataSourceId() {
            return null;
        }

        @Override
        public boolean isMatching(TenantDataSourceUser tenantDataSourceUser) {
            return false;
        }
    }

    @Override
    public Long getId() {
        return 1L;
    }

    @Override
    public SqlDataSourceType getSqlDataSourceType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Optional<Integer> getPort() {
        return Optional.ofNullable(port);
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public Optional<Integer> getLoginTimeout() {
        return Optional.ofNullable(loginTimeout);
    }

    @Override
    public String getTid() {
        return null;
    }

    @Override
    public Long getTimestamp() {
        return null;
    }

    @Override
    public List<TenantDataSourceUser> getTenantDataSourceUsers() {
        return List.of(user);
    }

    @Override
    public void addTenantDataSourceUsers(List<TenantDataSourceUser> tenantDataSourceUsers) {

    }

    @Override
    public boolean isMatching(TenantDataSource tenantDataSource) {
        return false;
    }
}
