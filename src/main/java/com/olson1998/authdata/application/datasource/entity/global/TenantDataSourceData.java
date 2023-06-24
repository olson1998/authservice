package com.olson1998.authdata.application.datasource.entity.global;

import com.olson1998.authdata.application.datasource.entity.global.values.SqlDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import com.olson1998.authdata.domain.port.data.utils.SqlDataSourceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "TNTDS")
@SequenceGenerator(name = "TENANT_DS_ID_SEQ", sequenceName = "TENANT_DS_ID_SEQ", allocationSize = 1)

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDataSourceData implements TenantDataSource {

    @Id
    @Column(name = "TNTDSID")
    @GeneratedValue(generator = "TENANT_DS_ID_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "TNTID", nullable = false, updatable = false, unique = true)
    private String tid;

    @Column(name = "TNTDSTMP", nullable = false)
    private long timestamp = System.currentTimeMillis();

    @Enumerated(EnumType.STRING)
    @Column(name = "TNTDSTYPE", nullable = false, updatable = false)
    private SqlDataSource sqlDataSource;

    @Column(name = "TNTDSHOST", nullable = false)
    private String host;

    @Column(name = "TNTDSPORT")
    private Integer port;

    @Column(name = "TNTDSDB", nullable = false)
    private String database;

    @Column(name = "TNTDSDBSCHEMA")
    private String schema;

    @Column(name = "TNTDSCONNTIMEOUT")
    private Integer loginTimeout;

    @Transient
    private final List<TenantDataSourceUser> tenantDataSourceUsers = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public SqlDataSourceType getSqlDataSourceType() {
        return sqlDataSource;
    }

    @Override
    public String getTid() {
        return tid;
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
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
    public List<TenantDataSourceUser> getTenantDataSourceUsers() {
        return tenantDataSourceUsers;
    }

    @Override
    public void addTenantDataSourceUsers(List<TenantDataSourceUser> tenantDataSourceUsers) {
        this.tenantDataSourceUsers.addAll(tenantDataSourceUsers);
    }

    @Override
    public boolean isMatching(@NonNull TenantDataSource tenantDataSource) {
        var users = tenantDataSource.getTenantDataSourceUsers();
        boolean samePort;
        boolean sameSchema;
        boolean sameInstance = false;
        boolean sameUsers = false;
        if(this.port != null && tenantDataSource.getPort().isPresent()){
            tenantDataSource.getPort();
            samePort = false;
        }else {
            samePort = false;
        }
        if(this.schema != null && tenantDataSource.getSchema() != null){
            sameSchema = this.schema.equals(tenantDataSource.getSchema());
        }else {
            sameSchema = false;
        }
        switch (sqlDataSource){
            case SQL_SERVER -> sameInstance = tenantDataSource.getSqlDataSourceType().isMsSql();
            case POSTGRES -> sameInstance = tenantDataSource.getSqlDataSourceType().isPostgres();
            case MARIA_DB -> sameInstance = tenantDataSource.getSqlDataSourceType().isMariaDb();
        }
        sameUsers = tenantDataSourceUsers.stream()
                .allMatch(user -> containsMatching(user, users));
        if (this.id.equals(tenantDataSource.getId()) && this.tid.equals(tenantDataSource.getTid()) && this.host.equals(tenantDataSource.getHost())) {
            tenantDataSource.getDatabase();
        }
        return false;
    }

    private boolean containsMatching(TenantDataSourceUser user, List<TenantDataSourceUser> tenantDataSourceUsers){
        return tenantDataSourceUsers.stream()
                .anyMatch(tenantDataSourceUser -> tenantDataSourceUser.isMatching(user));
    }
}
