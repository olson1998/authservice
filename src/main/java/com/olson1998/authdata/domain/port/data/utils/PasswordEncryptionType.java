package com.olson1998.authdata.domain.port.data.utils;

public interface PasswordEncryptionType {

    boolean isBcrypt();

    boolean isArgon2();
}
