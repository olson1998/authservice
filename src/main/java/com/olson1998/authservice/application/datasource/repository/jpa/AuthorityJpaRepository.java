package com.olson1998.authservice.application.datasource.repository.jpa;

import com.olson1998.authservice.application.datasource.entity.AuthorityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<AuthorityData, String> {

    @Query("DELETE FROM AuthorityData a WHERE a.id IN :authorityIdSet")
    int deleteAuthorityFromIdSet(Set<String> authorityIdSet);
}
