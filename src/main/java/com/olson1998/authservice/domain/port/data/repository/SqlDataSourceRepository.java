package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.data.entity.SqlDataSource;

import java.util.Optional;

public interface SqlDataSourceRepository {

    Optional<SqlDataSource> getTenantSqlDataSource(String tenant);
}
