package com.olson1998.authdata.application.datasource.repository.global.spring;

import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantDataSourceJpaRepository extends JpaRepository<TenantDataSourceData, Long> {

    @Query("SELECT ds FROM TenantDataSourceData ds WHERE ds.tid IN :tenants")
    List<TenantDataSourceData> selectTenantsDataSources(String [] tenants);

    @Query("SELECT CASE WHEN ds.timestamp=:timestamp THEN true ELSE false END FROM TenantDataSourceData ds WHERE ds.tid=:tid")
    boolean selectCaseIfGivenTimestampIsValid(String tid, Long timestamp);
}
