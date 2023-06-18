package com.olson1998.authdata.application.datasource.repository.tenant.spring;

import com.olson1998.authdata.application.datasource.entity.tenant.AuthorityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<AuthorityData, String> {

    @Query("SELECT DISTINCT a.id FROM AuthorityData a LEFT OUTER JOIN RoleBindingData rb ON a.id=rb.junction.authorityId WHERE rb.junction.roleId IN :roleIdsSet")
    Set<String> selectAuthorityFromIdSet(Set<String> roleIdsSet);

    @Modifying
    @Query("DELETE FROM AuthorityData a WHERE a.id IN :authorityIdSet")
    int deleteAuthorityFromIdSet(Set<String> authorityIdSet);

}
