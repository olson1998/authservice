package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authservice.domain.port.data.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTHROLE")

@NoArgsConstructor
@AllArgsConstructor
public class RoleData implements Role {

    @Id
    @Column(name = "ROLEID")
    private String id;

    @Column(name = "ROLENM", nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLESUB", nullable = false)
    private RoleSubject subject;

    @Column(name = "ROLETMP", nullable = false)
    private Long timestamp = System.currentTimeMillis();

    @Column(name = "ROLESUBUSERID")
    private Long userId;

    @Column(name = "ROLESUBCONO")
    private Long companyNumber;

    @Column(name = "ROLESUBREGID")
    private String regionId;

    @Column(name = "ROLESUBGRPID")
    private Long groupId;

    @Column(name = "ROLESUBTMID")
    private Long teamId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSubject() {
        return subject.name();
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }
}
