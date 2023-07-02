package com.olson1998.authdata.application.datasource.repository.tenant.wrapper;

import com.olson1998.authdata.application.datasource.entity.tenant.UserPasswordData;
import com.olson1998.authdata.application.datasource.repository.tenant.spring.UserPasswordJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.UserSecretDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPasswordJpaRepositoryWrapper implements UserSecretDataSourceRepository {

    private final UserPasswordJpaRepository userPasswordJpaRepository;

    @Override
    public void saveUserSecret(long userId, String password, Long expireTime) {
        userPasswordJpaRepository.save(new UserPasswordData(userId, password, expireTime));
    }

    @Override
    public int deleteUserSecret(long userId) {
        return userPasswordJpaRepository.deleteUserPasswordByUserId(userId);
    }
}
