package com.olson1998.authservice.application.datasource.repository.wrapper.main;

import com.olson1998.authservice.application.datasource.repository.jpa.main.SqlDataSourceJpaRepository;
import com.olson1998.authservice.domain.port.data.entity.SqlDataSource;
import com.olson1998.authservice.domain.port.data.repository.SqlDataSourceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SqlDataSourceJpaRepositoryWrapper implements SqlDataSourceRepository {

    private final SqlDataSourceJpaRepository sqlDataSourceJpaRepository;

    @Override
    public Optional<SqlDataSource> getTenantSqlDataSource(@NonNull String tenant) {
        return sqlDataSourceJpaRepository.selectSqlDataSourceByTenantId(tenant)
                .map(db -> Objects.requireNonNullElse(db, null));
    }
}
