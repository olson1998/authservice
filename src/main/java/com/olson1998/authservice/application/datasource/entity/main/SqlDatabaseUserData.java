package com.olson1998.authservice.application.datasource.entity.main;

import com.olson1998.authservice.application.datasource.entity.utils.PasswordDigest;
import com.olson1998.authservice.application.datasource.entity.utils.UserType;
import com.olson1998.authservice.domain.port.data.entity.SqlDatabaseUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TNTDBUSR")
@NoArgsConstructor
@AllArgsConstructor
public class SqlDatabaseUserData implements SqlDatabaseUser {

    @Id
    @Column(name = "USRID")
    private String id;

    @Column(name = "USRTNT", nullable = false)
    private String tenantId;

    @Column(name = "USRDBID", nullable = false)
    private String databaseId;

    @Column(name = "USRTP", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(name = "USRNM", nullable = false)
    private String username;

    @Column(name = "USRPASS", nullable = false)
    private String password;

    @Column(name = "USRDG", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PasswordDigest passwordDigest = PasswordDigest.NONE;

    public String getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEncryptedPassword() {
        return passwordDigest.encrypt(password);
    }
}
