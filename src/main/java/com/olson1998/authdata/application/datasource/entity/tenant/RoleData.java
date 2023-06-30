package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.application.datasource.entity.tenant.values.RoleSubject;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "AUTHROLE")

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleData implements Persistable<String>, Role{

    @Id
    @Column(name = "ROLEID")
    private String id;

    @Column(name = "ROLENM", nullable = false, updatable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLESUB", nullable = false, updatable = false)
    private RoleSubject subject;

    @Column(name = "ROLETMP", nullable = false)
    private Long timestamp = System.currentTimeMillis();

    @Column(name = "USERID", updatable = false)
    private Long userId;

    @Column(name = "ROLESUBCONO", updatable = false)
    private Long companyNumber;

    @Column(name = "ROLESUBREGID", updatable = false)
    private String regionId;

    @Column(name = "ROLESUBGRPID", updatable = false)
    private Long groupId;

    @Column(name = "ROLESUBTMID", updatable = false)
    private Long teamId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public Long getCompanyNumber() {
        return companyNumber;
    }

    @Override
    public String getSubject() {
        return subject.name();
    }

    @Override
    public String getRegionId() {
        return regionId;
    }

    @Override
    public Long getGroupId() {
        return groupId;
    }

    @Override
    public Long getTeamId() {
        return teamId;
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean isMatching(RoleDetails roleDetails) {
        var matchingUserId = false;
        var matchingCono = false;
        var matchingRegionId = false;
        var matchingGroupId = false;
        var matchingTeamId = false;
        if(this.userId != null && roleDetails.getUserId() != null){
            matchingUserId = this.userId.equals(roleDetails.getUserId());
        }else {
            matchingUserId = true;
        }
        if(this.companyNumber != null && roleDetails.getCompanyNumber() != null){
            matchingCono = this.companyNumber.equals(roleDetails.getCompanyNumber());
        }else {
            matchingCono = true;
        }
        if(this.regionId != null && roleDetails.getRegionId() != null){
            matchingRegionId = this.regionId.equals(roleDetails.getRegionId());
        }else {
            matchingRegionId = true;
        }
        if(this.groupId != null && roleDetails.getGroupId() != null){
            matchingGroupId = this.groupId.equals(roleDetails.getGroupId());
        }else {
            matchingGroupId = true;
        }
        if(this.teamId != null && roleDetails.getTeamId() != null){
            matchingTeamId = this.teamId.equals(roleDetails.getTeamId());
        }else {
            matchingTeamId = true;
        }return matchingUserId &&
                matchingCono &&
                matchingGroupId &&
                matchingRegionId &&
                matchingTeamId &&
                this.name.equals(roleDetails.getName()) &&
                this.subject.equals(roleDetails.getSubject());
    }

    public RoleData(@NonNull RoleDetails roleDetails) {
        this.subject = RoleSubject.valueOf(roleDetails.getSubject());
        this.name = roleDetails.getName();
        this.userId = roleDetails.getUserId();
        switch (this.subject){
            case COMPANY -> this.companyNumber = roleDetails.getCompanyNumber();
            case TEAM -> this.teamId = roleDetails.getTeamId();
            case REGION -> this.regionId = roleDetails.getRegionId();
            case GROUP -> this.groupId = roleDetails.getGroupId();
        }
    }



    @PrePersist
    public void generateId(){
        var idBuilder = new StringBuilder("ROLE&")
                .append(subject)
                .append('&');
        switch (subject){
            case PRIVATE -> idBuilder.append(userId);
            case COMPANY -> idBuilder.append(companyNumber);
            case TEAM -> idBuilder.append(teamId);
            case REGION -> idBuilder.append(regionId);
            case GROUP -> idBuilder.append(groupId);
        }
        idBuilder.append('&')
                .append(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
        this.id = idBuilder.toString();
    }
}
