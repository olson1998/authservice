package com.olson1998.authservice.application.data.entity;

import com.olson1998.authservice.domain.model.auth.data.UserDetails;
import com.olson1998.authservice.domain.port.data.entity.User;
import jakarta.persistence.*;
import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;
import java.util.UUID;

import static com.olson1998.authservice.application.data.entity.PasswordDigest.SHA256;
import static java.nio.charset.StandardCharsets.UTF_8;

@Entity
@Table(name = "AUTHUSR")
public class UserEntity implements User {

    @Id
    @Column(name = "USERID", unique = true)
    private String id;

    @Column(name = "USERNM", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "USERPASS", nullable = false)
    private String password;

    @Column(name = "USERPASSDIG", nullable = false, updatable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private PasswordDigest passwordDigest;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean verify(String password) {
        var digest = passwordDigest.toMessageDigest();
        var encPassBytes = DigestUtils.digest(digest, password.getBytes(UTF_8));
        var encPass = new String(encPassBytes, UTF_8);
        return this.password.equals(encPass);
    }

    public UserEntity(@NonNull UserDetails userDetails) {
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.id = Objects.requireNonNullElse(
                userDetails.getId(),
                UUID.randomUUID().toString()
        );
        this.passwordDigest = Objects.requireNonNullElse(
                PasswordDigest.valueOf(userDetails.getPasswordDigestAlgorithm()),
                SHA256
        );
    }
}
