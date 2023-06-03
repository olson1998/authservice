package com.olson1998.authservice.domain.port.processing.request.repository;

import com.olson1998.authservice.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authservice.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authservice.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorityRequestProcessor {

    @Transactional(rollbackFor = RollbackRequiredException.class)
    AuthoritySavingReport saveAuthorities(AuthoritySavingRequest authoritySavingRequest);
}
