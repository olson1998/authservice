package com.olson1998.authdata.domain.port.data.stereotype;

public interface User {

    Long getId();

    String getUsername();

    boolean verify(String password);
}
