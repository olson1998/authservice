package com.olson1998.authservice.application.requesting.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authservice.domain.port.request.entity.RoleDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.*;

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

    @JsonCreator
    public RoleDetailsForm(@JsonProperty(value = NAME, required = true) String name,
                           @JsonProperty(value = "subject", required = true) RoleSubject subject,
                           @JsonProperty(value = USER_ID) Long userId,
                           @JsonProperty(value = COMPANY_NUMBER) Long companyNumber,
                           @JsonProperty(value = REGION_ID) String regionId,
                           @JsonProperty(value = GROUP_ID) Long groupId,
                           @JsonProperty(value = TEAM_ID) Long teamId) {
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
