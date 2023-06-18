package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.values.SqlDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import com.olson1998.authdata.domain.port.data.utils.SqlDataSourceType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TNTDS")
@SequenceGenerator(name = "TENANT_DS_ID_SEQ", sequenceName = "TENANT_DS_ID_SEQ", allocationSize = 1)

@NoArgsConstructor
public class TenantDataSourceData implements TenantDataSource {

    @Id
    @Column(name = "TNTDSID")
    @GeneratedValue(generator = "TENANT_DS_ID_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "TNTID", nullable = false, updatable = false, unique = true)
    private String tid;

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
    public String getHost() {
        return host;
    }

    @Override
    public Integer getPort() {
        return port;
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
        if(this.port != null && tenantDataSource.getPort() != null){
            samePort = this.port.equals(tenantDataSource.getPort());
        }else {
            samePort = false;
        }
        if(this.schema != null && tenantDataSource.getSchema() != null){
            sameSchema = this.schema.equals(tenantDataSource.getSchema());
        }else {
            sameSchema = false;
        }
        switch (sqlDataSource){
            case MS_SQL -> sameInstance = tenantDataSource.getSqlDataSourceType().isMsSql();
            case POSTGRES -> sameInstance = tenantDataSource.getSqlDataSourceType().isPostgres();
            case MARIA_DB -> sameInstance = tenantDataSource.getSqlDataSourceType().isMariaDb();
        }
        sameUsers = tenantDataSourceUsers.stream()
                .allMatch(user -> containsMatching(user, users));
        return this.id.equals(tenantDataSource.getId()) &&
                this.tid.equals(tenantDataSource.getTid()) &&
                this.host.equals(tenantDataSource.getHost()) &&
                this.database.equals(tenantDataSource.getDatabase()) &&
                sameInstance &&
                samePort &&
                sameSchema &&
                sameUsers;
    }

    private boolean containsMatching(TenantDataSourceUser user, List<TenantDataSourceUser> tenantDataSourceUsers){
        return tenantDataSourceUsers.stream()
                .anyMatch(tenantDataSourceUser -> tenantDataSourceUser.isMatching(user));
    }
}
