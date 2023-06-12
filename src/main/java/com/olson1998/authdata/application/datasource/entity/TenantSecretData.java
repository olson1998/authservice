package com.olson1998.authdata.application.datasource.entity;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.application.datasource.entity.values.JwtAlgorithm;
import com.olson1998.authdata.domain.port.data.stereotype.TenantAlgorithm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Setter

@Entity
@Table(name = "TNTSCT")
@NoArgsConstructor
@AllArgsConstructor
public class TenantSecretData implements Persistable<String>, TenantAlgorithm {

    @Id
    @Column(name = "TNTSCTID")
    private String tenantId;

    @Column(name = "TNTSCTTMP", nullable = false)
    private Long timestamp;

    @Column(name = "TNTSCTSG", nullable = false, unique = true)
    private String secret;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TNTSCTALG", nullable = false)
    private JwtAlgorithm jwtAlgorithm;

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public Algorithm toAlgorithm() {
        switch (jwtAlgorithm){
            case HMAC256 -> {
                return Algorithm.HMAC256(secret);
            }
            case HMAC384 -> {
                return Algorithm.HMAC384(secret);
            }
            case HMAC512 -> Algorithm.HMAC512(secret);
        }
        return Algorithm.none();
    }

    @Override
    public String getId() {
        return tenantId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
