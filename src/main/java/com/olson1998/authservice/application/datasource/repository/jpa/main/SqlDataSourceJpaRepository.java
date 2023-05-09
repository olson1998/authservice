package com.olson1998.authservice.application.datasource.repository.jpa.main;

import com.olson1998.authservice.application.datasource.entity.main.SqlDataBaseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(transactionManager = "mainDatasourceTransactionManager")

@Repository
public interface SqlDataSourceJpaRepository extends JpaRepository<SqlDataBaseData, String> {

    @Query("SELECT db FROM SqlDataBaseData db WHERE db.tenantId=:tid")
    Optional<SqlDataBaseData> selectSqlDataSourceByTenantId(String tid);
}
