package com.olson1998.authdata.domain.port.data.repository;

import java.util.LinkedList;

public interface UserSecretDataSourceRepository {

    LinkedList<Byte> getUserSecretBytes(long userId);

    int saveUserSecret(long userId, byte[] secretBytes);

    int deleteUserSecret(long userId);
}
