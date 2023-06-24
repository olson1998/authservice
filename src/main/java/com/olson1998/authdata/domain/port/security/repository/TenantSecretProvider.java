package com.olson1998.authdata.domain.port.security.repository;

import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface TenantSecretProvider extends UserDetailsService {

    Optional<TenantSecret> getTenantSecret(String tid);

}
