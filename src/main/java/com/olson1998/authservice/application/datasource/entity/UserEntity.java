package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.domain.model.auth.data.UserDetails;
import com.olson1998.authservice.domain.port.data.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;
import java.util.UUID;

import static com.olson1998.authservice.application.datasource.entity.PasswordDigest.DEFAULT_DIGEST;
import static com.olson1998.authservice.application.datasource.entity.PasswordDigest.NONE;
import static jakarta.persistence.EnumType.ORDINAL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Entity
@Table(name = "AUTHUSR")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements User {

    @Id
    @Column(name = "USERID", unique = true)
    private String id;

    @Column(name = "USERNM", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "USERPASS", nullable = false)
    private String password;

    @Column(name = "USERPASSDIG", nullable = false, updatable = false)
    @Enumerated(value = ORDINAL)
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
        if(!passwordDigest.equals(NONE)){
            var digest = passwordDigest.toMessageDigest();
            var encPassBytes = DigestUtils.digest(digest, password.getBytes(UTF_8));
            var encPass = new String(encPassBytes, UTF_8);
            return this.password.equals(encPass);
        }else {
            return this.password.equals(password);
        }
    }

    public static UserEntity fromUserDetails(@NonNull UserDetails userDetails) {
        return new UserEntity(
                Objects.requireNonNullElse(
                        userDetails.getId(),
                        UUID.randomUUID().toString()
                ),
                userDetails.getUsername(),
                userDetails.getPassword(),
                Objects.requireNonNullElse(
                        PasswordDigest.ofUserDetails(userDetails),
                        DEFAULT_DIGEST
                )
        );
    }
}
