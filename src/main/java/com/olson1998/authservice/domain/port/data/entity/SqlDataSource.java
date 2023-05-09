package com.olson1998.authservice.domain.port.data.entity;

import javax.sql.DataSource;
import java.util.Set;

public interface SqlDataSource {

    String getTenantId();

    String getDataSourceType();

    String getUrl();

    int getPort();

    String getDatabase();

    Set<SqlDatabaseUser> getUsers();

    Set<SqlDataSourceEntityMapping> getEntityMappings();

    DataSource buildDataSource();

    DataSource buildDataSource(String userType);
}
