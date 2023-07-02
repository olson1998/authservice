package com.olson1998.authdata.application.datasource.entity.tenant.values;

import com.olson1998.authdata.domain.port.data.utils.PasswordEncryptionType;

public enum UserPasswordEncryption implements PasswordEncryptionType {

    B_CRYPT,
    ARGON2;

    @Override
    public boolean isBcrypt() {
        return this.equals(B_CRYPT);
    }

    @Override
    public boolean isArgon2() {
        return this.equals(ARGON2);
    }
}
