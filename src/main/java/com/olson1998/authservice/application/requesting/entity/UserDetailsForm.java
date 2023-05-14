package com.olson1998.authservice.application.requesting.entity;

import com.olson1998.authservice.application.datasource.entity.utils.SecretDigest;
import com.olson1998.authservice.domain.port.data.utils.SecretAlgorithm;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDetailsForm implements UserDetails {

    private final String username;

    private final String password;

    private final SecretDigest passwordDigest;

    @Override
    public SecretAlgorithm getSecretDigestAlgorithm() {
        return passwordDigest;
    }

}
