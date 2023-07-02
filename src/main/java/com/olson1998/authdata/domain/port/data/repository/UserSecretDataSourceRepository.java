package com.olson1998.authdata.domain.port.data.repository;

public interface UserSecretDataSourceRepository {

    void saveUserSecret(long userId, String password);

    int deleteUserSecret(long userId);
}
