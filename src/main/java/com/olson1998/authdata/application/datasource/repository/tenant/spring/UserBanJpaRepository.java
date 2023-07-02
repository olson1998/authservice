package com.olson1998.authdata.application.datasource.repository.tenant.spring;

import com.olson1998.authdata.application.datasource.entity.tenant.UserBansData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBanJpaRepository extends JpaRepository<UserBansData, String> {

}
