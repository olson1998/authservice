package com.olson1998.authservice.domain.port.request.stereotype.data;

import com.olson1998.authservice.domain.port.data.utils.SecretAlgorithm;

public interface UserDetails {

    String getUsername();

    String getPassword();

    SecretAlgorithm getSecretDigestAlgorithm();

}
