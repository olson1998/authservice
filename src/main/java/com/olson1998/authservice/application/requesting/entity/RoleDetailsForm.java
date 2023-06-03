package com.olson1998.authservice.application.requesting.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authservice.application.mapping.exception.RoleDetailsMappingException;
import com.olson1998.authservice.domain.port.processing.tree.exception.DtoMappingException;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.olson1998.authservice.application.requesting.entity.data.AbstractCommonJsonValues.*;

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
                           @JsonProperty(value = TEAM_ID) Long teamId) throws DtoMappingException {
        this.name = name;
        this.subject = subject;
        setValueOrThrow(
                subject,
                userId,
                companyNumber,
                regionId,
                groupId,
                teamId
        );
    }

    @Override
    public String getSubject() {
        return subject.name();
    }

    private void setValueOrThrow(RoleSubject subject,
                                 Long userId,
                                 Long companyNumber,
                                 String regionId,
                                 Long groupId,
                                 Long teamId) throws DtoMappingException {
        switch (subject){
            case PRIVATE -> {
                throwMappingExceptionIfValueNull(subject, userId);
                this.userId = userId;
            }
            case REGION -> {
                throwMappingExceptionIfValueNull(subject, regionId);
                this.regionId = regionId;
            }
            case GROUP -> {
                throwMappingExceptionIfValueNull(subject, groupId);
                this.groupId = groupId;
            }
            case TEAM -> {
                throwMappingExceptionIfValueNull(subject, teamId);
                this.teamId = teamId;
            }
            case COMPANY -> {
                throwMappingExceptionIfValueNull(subject, companyNumber);
                this.companyNumber = companyNumber;
            }
        }
    }

    private void throwMappingExceptionIfValueNull(RoleSubject subject, Object value) throws DtoMappingException {
        if(value == null){
            String jsonKey = null;
            switch (subject){
                case PRIVATE -> jsonKey = USER_ID;
                case COMPANY -> jsonKey = COMPANY_NUMBER;
                case REGION -> jsonKey = REGION_ID;
                case GROUP -> jsonKey= GROUP_ID;
                case TEAM -> jsonKey = TEAM_ID;
            }
            var reason = String.format("subject: '%s', requires non null value of json key: '%s'", subject, jsonKey);
            throw new RoleDetailsMappingException(RoleDetails.class, reason);
        }
    }
}
