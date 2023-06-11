package com.olson1998.authdata.domain.port.security;

import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;

import java.util.Optional;

public interface TenantSecretProvider {

    Optional<TenantSecret> getTenantSecret(String tid);

}
