package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.application.datasource.entity.utils.PasswordDigest;
import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;

import static com.olson1998.authservice.application.datasource.entity.utils.PasswordDigest.DEFAULT_DIGEST;

@Entity
@Table(name = "AUTHUSER")
@SequenceGenerator(name = "AUTH_USER_NUM_SEQ", sequenceName = "AUTH_USER_NUM_SEQ", allocationSize = 1)

@NoArgsConstructor
public class UserData implements User {

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
    private PasswordDigest passwordDigest;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean verify(String password) {
        return this.password.equals(
                passwordDigest.encrypt(password)
        );
    }

    public UserData(@NonNull UserDetails userDetails) {
        var digest = Objects.requireNonNullElse(
                PasswordDigest.ofUserDetails(userDetails),
                DEFAULT_DIGEST
        );
        this.username = userDetails.getUsername();
        this.password = digest.encrypt(userDetails.getPassword());
        this.passwordDigest = digest;
    }

    public UserData(String username, String password, PasswordDigest passwordDigest) {
        this.username = username;
        this.password = password;
        this.passwordDigest = passwordDigest;
    }
}
