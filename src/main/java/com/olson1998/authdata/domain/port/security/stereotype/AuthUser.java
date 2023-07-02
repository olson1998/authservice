package com.olson1998.authdata.domain.port.security.stereotype;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public abstract class AuthUser extends RegisteredClient {

    public abstract boolean isEnabled();

    public abstract boolean isBanned();

    public abstract Long getExpireTime();
}
