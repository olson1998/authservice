package com.olson1998.authservice.domain.port.data.entity;

public interface SqlDatabaseUser {

    String getId();

    String getUsername();

    String getEncryptedPassword();

}
