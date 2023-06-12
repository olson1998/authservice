package com.olson1998.authdata.application.datasource.repository.jpa;

import com.olson1998.authdata.application.datasource.entity.TrustedIssuerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrustedIssuerDataJpaRepository extends JpaRepository<TrustedIssuerData, String> {
}
