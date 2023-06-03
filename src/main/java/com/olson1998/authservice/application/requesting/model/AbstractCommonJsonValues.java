package com.olson1998.authservice.application.requesting.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AbstractCommonJsonValues {

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String USER_ID = "uid";

    public static final String COMPANY_NUMBER = "cono";

    public static final String REGION_ID = "regid";

    public static final String GROUP_ID = "grpid";

    public static final String TEAM_ID ="tmid";
}
