package com.olson1998.authdata.application.developer.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.application.datasource.LocalThreadTenantDataSource;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j

@Profile("developer")

@Component
@ConditionalOnProperty("com.olson1998.authdata.adapter.inbound.jwt-developer-controller.allowed")
public class JwtTokenFactory {

    @Getter(value = AccessLevel.PROTECTED)
    private final String serviceIpPort;

    private final TenantSecretProvider tenantSecretProvider;

    public JwtTokenFactory(TenantSecretProvider tenantSecretProvider,
                           @Value("${server.port}") int port) {
        this.tenantSecretProvider = tenantSecretProvider;
        this.serviceIpPort = new StringBuilder(InetAddress.getLoopbackAddress().getHostAddress())
                .append(':')
                .append(port)
                .toString();
    }

    public CompletableFuture<String> createDeveloperJwt(String tenantId, long userId){
        return CompletableFuture.supplyAsync(()-> tenantSecretProvider.getTenantSecret(tenantId))
                .thenApply(Optional::orElseThrow)
                .thenApply(TenantSecret::toAlgorithm)
                .thenApply(alg -> writeJwt(tenantId, userId, alg));
    }

    private String writeJwt(String tid, long userId, Algorithm algorithm){
        log.info("Building developer token for tenant: '{}', user: '{}' with audience: '{}'",
                tid,
                userId,
                serviceIpPort
        );
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(serviceIpPort)
                .withClaim("tid", tid)
                .withClaim("uid", userId)
                .withAudience(serviceIpPort)
                .withIssuer(serviceIpPort)
                .withExpiresAt(new Date(System.currentTimeMillis() + Duration.ofDays(1).toMillis()))
                .sign(algorithm);
    }
}
