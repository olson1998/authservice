package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.model.processing.report.DomainAuthUser;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserSecretDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthUserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthUserObtainRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.OBTAIN;

@Slf4j

@RequiredArgsConstructor
public class AuthUserProcessingService implements AuthUserRequestProcessor {

    private final UserDataSourceRepository userDataSourceRepository;

    private final UserSecretDataSourceRepository userSecretDataSourceRepository;

    @Override
    public AuthUser getAuthUser(AuthUserObtainRequest authUserObtainRequest) {
        ProcessingRequestLogger.log(log, authUserObtainRequest, OBTAIN, AuthUser.class);
        var userId = authUserObtainRequest.getUserId();
        var user = userDataSourceRepository.getUser(userId)
                .orElseThrow();
        var passwordBytes = userSecretDataSourceRepository.getUserSecretBytes(userId);
        return new DomainAuthUser(user, passwordBytes);
    }
}
