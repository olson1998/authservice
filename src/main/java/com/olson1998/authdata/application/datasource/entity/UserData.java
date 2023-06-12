package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.values.SecretDigest;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Persistable;

import java.util.Objects;

import static com.olson1998.authdata.application.datasource.entity.values.SecretDigest.DEFAULT_DIGEST;

@Entity
@Table(name = "AUTHUSER")
@SequenceGenerator(name = "AUTH_USER_NUM_SEQ", sequenceName = "AUTH_USER_NUM_SEQ", allocationSize = 1)

@NoArgsConstructor
public class UserData implements Persistable<Long>, User {

    @Id
    @Column(name = "USERNUM")
    @GeneratedValue(generator = "AUTH_USER_NUM_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "USERNM", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "USERPASS", nullable = false)
    private String password;

    @Column(name = "USERPASSALG", nullable = false, updatable = false)
    @Enumerated(value = EnumType.STRING)
    private SecretDigest secretDigest;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean verify(String password) {
        return this.password.equals(
                secretDigest.encrypt(password)
        );
    }

    public UserData(@NonNull UserDetails userDetails) {
        var digest = Objects.requireNonNullElse(
                SecretDigest.ofUserDetails(userDetails),
                DEFAULT_DIGEST
        );
        this.username = userDetails.getUsername();
        this.password = digest.encrypt(userDetails.getPassword());
        this.secretDigest = digest;
    }

    public UserData(Long id, String username, String password, SecretDigest secretDigest) {
        this.id = id;
        this.username = username;
        this.password = secretDigest.encrypt(password);
        this.secretDigest = secretDigest;
    }
}
