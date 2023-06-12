package com.olson1998.authdata.domain.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.olson1998.authdata.domain.model.exception.security.CheckpointRequiredException;
import com.olson1998.authdata.domain.model.exception.security.TenantSecretNotFound;
import com.olson1998.authdata.domain.port.security.repository.CheckpointProvider;
import com.olson1998.authdata.domain.port.security.repository.RequestContextFactory;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.repository.TokenVerifier;
import com.olson1998.authdata.domain.port.security.stereotype.CheckpointContext;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.NonNull;

import java.net.InetAddress;

import static com.olson1998.authdata.domain.service.security.RequestContextFabricationService.TENANT_ID;

public class TokenVerifyingService implements TokenVerifier {

    private final String serviceIpPort;

    private final CheckpointProvider checkpointProvider;

    private final RequestContextFactory requestContextFactory;

    private final TenantSecretProvider tenantSecretProvider;

    @Override
    public RequestContext verifyJwt(@NonNull String token) {
        var decodedToken = JWT.decode(token);
        var tid = decodedToken.getClaim(TENANT_ID).asString();
        var tenantSecret = tenantSecretProvider.getTenantSecret(tid)
                .orElseThrow(TenantSecretNotFound::new);
        var verifier = buildVerifier(tenantSecret);
        verifier.verify(decodedToken);
        return requestContextFactory.fabricate(decodedToken);
    }

    @Override
    public CheckpointContext verifyCheckpointToken(String xCheckpointToken) {
        var checkpointTimestamp = checkpointProvider.getCheckpointTimestamp(xCheckpointToken)
                .orElseThrow(CheckpointRequiredException::new);
        var tid = checkpointTimestamp.getTenantId();
        var tenantSecret = tenantSecretProvider.getTenantSecret(tid)
                .orElseThrow(TenantSecretNotFound::new);
        var alg = tenantSecret.toAlgorithm();
        var checkpoint = checkpointProvider.getCheckpoint(
                xCheckpointToken,
                checkpointTimestamp,
                alg
        ).orElseThrow(CheckpointRequiredException::new);
        checkpoint.verifyCheckpointToken(xCheckpointToken, alg.toString());
        return requestContextFactory.fabricate(xCheckpointToken, checkpointTimestamp, checkpoint);
    }

    private JWTVerifier buildVerifier(TenantSecret tenantSecret){
        var alg = tenantSecret.toAlgorithm();
        var tid = tenantSecret.getTenantId();
        var trustedIssuers = tenantSecret.getTrustedIssuers();
        return JWT.require(alg)
                .withAudience(serviceIpPort)
                .withClaim(TENANT_ID, tid)
                .withIssuer(trustedIssuers)
                .build();
    }

    public TokenVerifyingService(InetAddress serviceAddress,
                                 int servicePort,
                                 CheckpointProvider checkpointProvider,
                                 RequestContextFactory requestContextFactory,
                                 TenantSecretProvider tenantSecretProvider) {
        this.requestContextFactory = requestContextFactory;
        this.tenantSecretProvider = tenantSecretProvider;
        this.checkpointProvider = checkpointProvider;
        this.serviceIpPort = new StringBuilder(serviceAddress.getHostAddress())
                .append(':')
                .append(servicePort)
                .toString();
    }

}
