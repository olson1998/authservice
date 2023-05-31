package com.olson1998.authservice.domain.port.data.stereotype;

public interface User {

    Long getId();

    String getUsername();

    boolean verify(String password);
}
