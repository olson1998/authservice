package com.olson1998.authdata.application.datasource.repository.jpa;

import com.olson1998.authdata.application.datasource.entity.TenantDataSourceUserData;
import com.olson1998.authdata.application.datasource.entity.id.TenantDataSourceUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantDataSourceUserJpaRepository extends JpaRepository<TenantDataSourceUserData, TenantDataSourceUserId> {

    @Query("SELECT usr FROM TenantDataSourceUserData usr WHERE usr.tenantDataSourceUserId.tid=:tid")
    List<TenantDataSourceUserData> selectTenantDataSourceUsersByTenantId(String tid);
}
