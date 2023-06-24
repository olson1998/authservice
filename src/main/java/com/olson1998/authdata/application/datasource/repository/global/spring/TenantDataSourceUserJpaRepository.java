package com.olson1998.authdata.application.datasource.repository.global.spring;

import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceUserData;
import com.olson1998.authdata.application.datasource.entity.global.id.TenantDataSourceUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantDataSourceUserJpaRepository extends JpaRepository<TenantDataSourceUserData, TenantDataSourceUserId> {

    @Query("SELECT usr FROM TenantDataSourceUserData usr " +
            "LEFT OUTER JOIN TenantDataSourceData ds " +
            "ON usr.userId.dataSourceId=ds.id " +
            "WHERE ds.tid=:tid")
    List<TenantDataSourceUserData> selectTenantDataSourceUsersByTenantId(String tid);
}
