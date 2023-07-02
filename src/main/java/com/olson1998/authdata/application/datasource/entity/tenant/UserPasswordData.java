package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.domain.port.data.stereotype.UserPassword;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "USERSECRET")

@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordData implements Persistable<Long>, UserPassword {

    @Id
    @Column(name = "USERID")
    private Long userId;

    @Column(name = "USERSECRET")
    private String password;

    @Column(name = "USERSECRETEXP")
    private Long expireTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    @Override
    public boolean isExpiring() {
        return expireTime != null;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
