package com.olson1998.authdata.domain.port.security.repository;

import com.olson1998.authdata.domain.port.data.utils.PasswordEncryptionType;

public interface UserPasswordEnigma {

    String getEncryptedPassword(PasswordEncryptionType encryptionType, String password);
}
