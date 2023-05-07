package com.olson1998.authservice.domain.port.data.entity;

public interface User {

    String getId();

    String getUsername();

    boolean verify(String password);
}
