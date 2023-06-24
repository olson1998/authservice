package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.utils.SecretEncryption;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;

@Getter
@RequiredArgsConstructor
public class DomainAuthUser implements AuthUser {

    @JsonProperty(value = "id")
    private final Long id;

    @JsonProperty(value = "username")
    private final String username;

    @JsonProperty(value = "algorithm")
    private final SecretEncryption secretEncryptor;

    @JsonProperty(value = "enc_password")
    private final LinkedList<Byte> passwordBytes;

    public DomainAuthUser(@NonNull User user,@NonNull LinkedList<Byte> passwordBytes) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.secretEncryptor = user.getSecretEncryptor();
        this.passwordBytes = passwordBytes;
    }
}
