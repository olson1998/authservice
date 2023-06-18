package com.olson1998.authdata.domain.port.processing.request.repository;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthorityDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorityRequestProcessor {

    @Transactional(rollbackFor = RollbackRequiredException.class, transactionManager = "tenantDatasourceTransactionManager")
    AuthoritySavingReport saveAuthorities(AuthoritySavingRequest authoritySavingRequest);

    @Transactional(rollbackFor = RollbackRequiredException.class, transactionManager = "tenantDatasourceTransactionManager")
    AuthorityDeletingReport deleteAuthorities(AuthorityDeletingRequest authorityDeletingRequest);
}
