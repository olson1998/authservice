package com.olson1998.authdata.domain.port.data.repository;

import java.util.LinkedList;

public interface UserSecretDataSourceRepository {

    LinkedList<Byte> getUserSecretBytes(String username);

    int saveUserSecret(long userId, byte[] secretBytes);

    int deleteUserSecret(long userId);
}
