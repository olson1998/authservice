package com.olson1998.authdata.application.datasource.repository.global.spring;

import com.olson1998.authdata.application.datasource.entity.global.TrustedIssuerData;
import com.olson1998.authdata.application.datasource.entity.global.id.TrustedIssuerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrustedIssuerDataJpaRepository extends JpaRepository<TrustedIssuerData, TrustedIssuerId> {
}
