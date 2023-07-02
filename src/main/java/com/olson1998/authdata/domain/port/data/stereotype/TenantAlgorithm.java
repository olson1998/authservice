package com.olson1998.authdata.domain.port.data.stereotype;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.domain.port.data.utils.PasswordEncryptionType;

public interface TenantAlgorithm {

    String getTenantId();

    long getTimestamp();

    PasswordEncryptionType getPasswordEncryptionType();

    Algorithm toAlgorithm();

}
