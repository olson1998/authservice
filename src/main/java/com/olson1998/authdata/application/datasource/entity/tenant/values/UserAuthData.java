package com.olson1998.authdata.application.datasource.entity.tenant.values;

import com.olson1998.authdata.domain.port.security.stereotype.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class UserAuthData extends AuthUser {

    private final Long id;

    private final String username;

    private final String password;

    private final Long idIssuingTime;

    private final Long expireTime;

    private final Long passwordExpireTime;

    private final Boolean enabled;

    private final Boolean banned;


    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getClientId() {
        return String.valueOf(id);
    }

    @Override
    public String getClientName() {
        return username;
    }

    @Override
    public String getClientSecret() {
        return password;
    }

    @Override
    public Instant getClientIdIssuedAt() {
        return Instant.ofEpochSecond(idIssuingTime);
    }

    @Override
    public Instant getClientSecretExpiresAt() {
        return Objects.requireNonNullElse(Instant.ofEpochSecond(passwordExpireTime), null);
    }

    @Override
    public Set<String> getPostLogoutRedirectUris() {
        return super.getPostLogoutRedirectUris();
    }

    @Override
    public TokenSettings getTokenSettings() {
        return super.getTokenSettings();
    }


    @Override
    public boolean isEnabled() {
        return Objects.requireNonNullElse(enabled, false);
    }

    @Override
    public boolean isBanned() {
        return Objects.requireNonNullElse(banned, false);
    }

    @Override
    public Long getExpireTime() {
        return expireTime;
    }
}
