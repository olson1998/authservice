package com.olson1998.authdata.application.requesting.model.payload;

import com.olson1998.authdata.application.datasource.entity.utils.SecretDigest;
import com.olson1998.authdata.domain.port.data.utils.SecretAlgorithm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
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
