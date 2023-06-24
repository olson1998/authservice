package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.application.datasource.entity.tenant.values.SecretDigest;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.utils.SecretAlgorithm;
import com.olson1998.authdata.domain.port.data.utils.SecretEncryption;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Persistable;

import java.util.Objects;

import static com.olson1998.authdata.application.datasource.entity.tenant.values.SecretDigest.DEFAULT_DIGEST;

@Entity
@Table(name = "AUTHUSER")
@SequenceGenerator(name = "AUTH_USER_ID_SEQ", sequenceName = "AUTH_USER_ID_SEQ", allocationSize = 1)

@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Persistable<Long>, User {

    @Id
    @Column(name = "USERID")
    @GeneratedValue(generator = "AUTH_USER_ID_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "USERNM", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "USERPASSALG", nullable = false, updatable = false)
    @Enumerated(value = EnumType.ORDINAL)
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
    public SecretEncryption getSecretEncryptor() {
        return secretDigest;
    }

    public UserData(@NonNull UserDetails userDetails) {
        var digest = Objects.requireNonNullElse(
                SecretDigest.ofUserDetails(userDetails),
                DEFAULT_DIGEST
        );
        this.username = userDetails.getUsername();
        this.secretDigest = digest;
    }

}
