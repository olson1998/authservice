package com.olson1998.authservice.application.requesting.entity;

import com.olson1998.authservice.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authservice.domain.port.request.entity.RoleDetails;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleDetailsForm implements RoleDetails {

    @Getter
    private final String name;

    private final RoleSubject subject;

    @Getter
    private Long userId;

    @Getter
    private Long companyNumber;

    @Getter
    private String regionId;

    @Getter
    private Long groupId;

    @Getter
    private Long teamId;

    public RoleDetailsForm(@NonNull String name,
                           @NonNull RoleSubject subject,
                           Long userId,
                           Long companyNumber,
                           String regionId,
                           Long groupId,
                           Long teamId) {
        this.name = name;
        this.subject = subject;
        switch (subject){
            case PRIVATE -> this.userId=userId;
            case TEAM -> this.teamId=teamId;
            case GROUP -> this.groupId=groupId;
            case REGION -> this.regionId = regionId;
            case COMPANY -> this.companyNumber=companyNumber;
        }
    }

    @Override
    public String getSubject() {
        return subject.name();
    }
}
