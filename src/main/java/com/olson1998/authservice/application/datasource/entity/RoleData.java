package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.request.data.RoleDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;

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

    public RoleData(@NonNull RoleDetails roleDetails) {
        this.subject = RoleSubject.valueOf(roleDetails.getSubject());
        this.name = roleDetails.getName();
        switch (this.subject){
            case PRIVATE -> this.userId = roleDetails.getUserId();
            case COMPANY -> this.companyNumber = roleDetails.getCompanyNumber();
            case TEAM -> this.teamId = roleDetails.getTeamId();
            case REGION -> this.regionId = roleDetails.getRegionId();
            case GROUP -> this.groupId = roleDetails.getGroupId();
        }
    }

    @PrePersist
    public void generateId(){
        var idBuilder = new StringBuilder("ROLE_")
                .append(subject)
                .append('_');
        switch (subject){
            case PRIVATE -> idBuilder.append(userId);
            case COMPANY -> idBuilder.append(companyNumber);
            case TEAM -> idBuilder.append(teamId);
            case REGION -> idBuilder.append(regionId);
            case GROUP -> idBuilder.append(groupId);
        }
        idBuilder.append('_')
                .append(RandomStringUtils.randomAlphabetic(6).toUpperCase());
        this.id = idBuilder.toString();
    }
}
