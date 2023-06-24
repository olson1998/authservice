package com.olson1998.authdata.domain.port.data.stereotype;

import com.olson1998.authdata.domain.port.data.utils.SecretEncryption;

public interface User {

    Long getId();

    String getUsername();

    SecretEncryption getSecretEncryptor();
}
