package com.olson1998.authservice.application.datasource.repository.jpa;

import com.olson1998.authservice.application.datasource.entity.UserMembershipData;
import com.olson1998.authservice.application.datasource.entity.id.UserMembershipJunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMembershipJpaRepository extends JpaRepository<UserMembershipData, UserMembershipJunction> {

    @Query("DELETE FROM UserMembershipData um WHERE um.junction.userId=:userId")
    int deleteUserMembership(long userId);
}
