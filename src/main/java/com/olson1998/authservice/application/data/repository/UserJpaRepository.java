package com.olson1998.authservice.application.data.repository;

import com.olson1998.authservice.application.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u WHERE u.username=:username")
    Optional<UserEntity> getUserByUsername(String username);

}
