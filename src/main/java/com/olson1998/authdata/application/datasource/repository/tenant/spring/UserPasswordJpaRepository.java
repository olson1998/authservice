package com.olson1998.authdata.application.datasource.repository.tenant.spring;

import com.olson1998.authdata.application.datasource.entity.tenant.UserPasswordData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordJpaRepository extends JpaRepository<UserPasswordData, Long> {

    @Modifying
    @Query("DELETE FROM UserPasswordData pass WHERE pass.userId=:userId")
    int deleteUserPasswordByUserId(long userId);
}
