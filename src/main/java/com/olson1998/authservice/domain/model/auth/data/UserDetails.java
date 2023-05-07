package com.olson1998.authservice.domain.model.auth.data;

public interface UserDetails {

    String getId();

    String getUsername();

    String getPassword();

    String getPasswordDigestAlgorithm();

    void withId(String id);

    void withPasswordDigestAlgorithm(String algorithm);
}
