package com.olson1998.authservice.application.requesting.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.datasource.entity.utils.SecretDigest;
import com.olson1998.authservice.domain.port.data.utils.SecretAlgorithm;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsForm implements UserDetails {

    @JsonProperty(value = "username", required = true)
    private String username;

    @JsonProperty(value = "password", required = true)
    private String password;

    @JsonProperty(value = "digest")
    private SecretDigest passwordDigest;

    @Override
    public SecretAlgorithm getSecretDigestAlgorithm() {
        return passwordDigest;
    }
}
