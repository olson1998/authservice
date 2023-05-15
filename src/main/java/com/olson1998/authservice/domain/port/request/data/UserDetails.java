package com.olson1998.authservice.domain.port.request.data;

import com.olson1998.authservice.domain.port.data.utils.SecretAlgorithm;

public interface UserDetails {

    String getUsername();

    String getPassword();

    SecretAlgorithm getSecretDigestAlgorithm();

}
