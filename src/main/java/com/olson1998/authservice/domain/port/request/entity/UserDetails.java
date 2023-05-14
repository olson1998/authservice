package com.olson1998.authservice.domain.port.request.entity;

public interface UserDetails {

    String getUsername();

    String getPassword();

    String getPasswordDigestAlgorithm();


}
