package com.olson1998.authservice.application.datasource.entity.main;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.olson1998.authservice.application.datasource.entity.utils.DataSourceType;
import com.olson1998.authservice.application.datasource.entity.utils.UserType;
import com.olson1998.authservice.domain.port.data.entity.SqlDataSource;
import com.olson1998.authservice.domain.port.data.entity.SqlDataSourceEntityMapping;
import com.olson1998.authservice.domain.port.data.entity.SqlDatabaseUser;
import jakarta.persistence.*;
import lombok.SneakyThrows;
import org.mariadb.jdbc.MariaDbDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.olson1998.authservice.application.datasource.entity.utils.UserType.RW;

@Entity
@Table(name = "TNTDS")
public class SqlDataBaseData implements SqlDataSource {

    @Id
    @Column(name = "DBID")
    private String id;

    @Column(name = "DBTNT", unique = true, nullable = false)
    private String tenantId;

    @Column(name = "DBTYPE")
    @Enumerated(value = EnumType.STRING)
    private DataSourceType dataSourceType;

    @Column(name = "DBURL", nullable = false)
    private String url;

    @Column(name = "DBPORT", nullable = false)
    private int port;

    @Column(name = "DBNAME", nullable = false)
    private String database;

    @Column(name = "DBLGNTIMEOUT")
    private int loginTimeout = 45;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns(value = {
            @JoinColumn(name = "DBID", referencedColumnName = "USRDBID", nullable = false),
            @JoinColumn(name = "DBTNT", referencedColumnName = "USRTNT", nullable = false)
    })
    private Set<SqlDatabaseUserData> users;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns(value = {
            @JoinColumn(name = "DBID", referencedColumnName = "MAPDBID", nullable = false),
            @JoinColumn(name = "DBTNT", referencedColumnName = "MAPTNT", nullable = false)
    })
    private Set<SqlDataSourceEntityMappingData> entityMappings;

    @Override
    public DataSource buildDataSource() {
        return buildDataSource(RW.name());
    }

    @Override
    public DataSource buildDataSource(String userType) {
        var type = UserType.valueOf(userType);
        var user = searchForUser(type);
        switch (dataSourceType){
            case MS_SQL_SERVER -> {
                return buildSqlServerDataSource(user);
            }
            case MARIADB -> {
                return buildMariaDbDataSource(user);
            }
            case POSTGRES -> {
                return buildPostgresDataSource(user);
            }default -> throw new IllegalStateException("no user found");
        }
    }

    private SQLServerDataSource buildSqlServerDataSource(SqlDatabaseUser user){
        var sqlServerDataSource = new SQLServerDataSource();
        sqlServerDataSource.setServerName(url);
        sqlServerDataSource.setPortNumber(port);
        sqlServerDataSource.setLoginTimeout(loginTimeout);
        sqlServerDataSource.setUser(user.getUsername());
        sqlServerDataSource.setPassword(user.getEncryptedPassword());
        return sqlServerDataSource;
    }

    @SneakyThrows
    private MariaDbDataSource buildMariaDbDataSource(SqlDatabaseUser user){
        var mariaDbDataSource = new MariaDbDataSource();
        var mariaDbUrl = this.url +  ":" + port + "/" + database;
        mariaDbDataSource.setUrl(mariaDbUrl);
        mariaDbDataSource.setLoginTimeout(loginTimeout);
        mariaDbDataSource.setUser(user.getUsername());
        mariaDbDataSource.setPassword(user.getEncryptedPassword());
        return mariaDbDataSource;
    }

    private PGSimpleDataSource buildPostgresDataSource(SqlDatabaseUser user){
        var pgDataSource = new PGSimpleDataSource();
        pgDataSource.setUrl(url);
        pgDataSource.setDatabaseName(database);
        pgDataSource.setPortNumbers(new int[]{port});
        pgDataSource.setLoginTimeout(loginTimeout);
        pgDataSource.setUser(user.getUsername());
        pgDataSource.setPassword(user.getEncryptedPassword());
        return pgDataSource;
    }

    private SqlDatabaseUserData searchForUser(UserType type){
        return users.stream()
                .filter(user -> user.getUserType().equals(type))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public String getDataSourceType() {
        return dataSourceType.name();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public Set<SqlDatabaseUser> getUsers() {
        return users.stream()
                .map(user -> Objects.requireNonNullElse(user, null))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SqlDataSourceEntityMapping> getEntityMappings() {
        return entityMappings.stream()
                .map(mapping -> Objects.requireNonNullElse(mapping, null))
                .collect(Collectors.toSet());
    }
}
