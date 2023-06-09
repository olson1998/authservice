package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.Objects;

@Entity
@Table(name = "AUTHUSER")

@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Persistable<Long>, User {

    @Id
    @Column(name = "USERID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNM", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "USERENABLED", nullable = false)
    private Boolean enabled;

    @Column(name = "USEREXPTIME")
    private Long expireTime;

    @Column(name = "USERIDISSTIME", nullable = false, updatable = false)
    private Long idIssueTime;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Long getExpireTime() {
        return expireTime;
    }

    @Override
    public Long getIdIssuingTime() {
        return idIssueTime;
    }

    @Override
    public boolean isEnabled() {
        return Objects.requireNonNullElse(enabled, false);
    }

    @Override
    public boolean isExpiring() {
        return expireTime != null;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public UserData(UserDetails userDetails, long timestamp) {
        this.username = userDetails.getUsername();
        this.idIssueTime = timestamp;
        this.enabled = userDetails.isEnabled();
        if(userDetails.isUserExpiring()){
            this.expireTime = timestamp + userDetails.getUserExpDuration().toMillis();
        }
    }
}
