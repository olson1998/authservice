package com.olson1998.authdata.application.datasource.repository.tenant.wrapper;

import com.olson1998.authdata.application.datasource.entity.tenant.UserSecretByteData;
import com.olson1998.authdata.application.datasource.repository.tenant.spring.UserSecretByteJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.UserSecretDataSourceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class UserSecretByteJpaRepositoryWrapper implements UserSecretDataSourceRepository {

    private final UserSecretByteJpaRepository userSecretByteJpaRepository;

    @Override
    public LinkedList<Byte> getUserSecretBytes(@NonNull String username) {
        var userSecretBytes = userSecretByteJpaRepository.selectUserSecretBytesByUsername(username);
        return new LinkedList<>(userSecretBytes);
    }

    @Override
    public int saveUserSecret(long userId, byte[] secretBytes) {
        var userSecretBytes = new ArrayList<UserSecretByteData>();
        var length = secretBytes.length;
        for(int i =0; i < length; i++){
            var byteVal = secretBytes[i];
            var userSecretByte = new UserSecretByteData(userId, i, byteVal);
            userSecretBytes.add(userSecretByte);
        }
        return userSecretByteJpaRepository.saveAll(userSecretBytes).size();
    }
}
