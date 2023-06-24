package com.olson1998.authdata.domain.port.data.utils;

public interface SecretEncryption {

    byte[] encrypt(String password);
}
