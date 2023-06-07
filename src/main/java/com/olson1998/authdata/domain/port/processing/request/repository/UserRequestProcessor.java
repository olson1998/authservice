package com.olson1998.authdata.domain.port.processing.request.repository;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipBindRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import org.springframework.transaction.annotation.Transactional;

public interface UserRequestProcessor {

    /**
     * Save new user and all bindings
     * @param request Request containing all required data to perform persisting
     * @return save user
     */
    @Transactional(rollbackFor = RollbackRequiredException.class)
    UserSavingReport saveUser(UserSavingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class)
    UserDeletingReport deleteUser(UserDeletingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class)
    UserMembershipBindReport bindMemberships(UserMembershipBindRequest request);
}
