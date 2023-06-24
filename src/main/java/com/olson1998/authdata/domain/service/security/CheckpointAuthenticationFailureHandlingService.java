package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class CheckpointAuthenticationFailureHandlingService implements CheckpointAuthenticationFailureHandler {

    private static final String X_CHECKPOINT_FAILURE_ERROR_CLASS = "X-Checkpoint-auth-fail-error";

    private static final String X_CHECKPOINT_FAILURE_ERROR_REASON = "X-Checkpoint-auth-fail-reason";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        var statusCode = 401;
        var stackTrace = ExceptionUtils.getStackTrace(exception);
        if(exception instanceof CheckpointVerificationException verificationException){
            statusCode = verificationException.getStatusCode();
            response.addHeader(X_CHECKPOINT_FAILURE_ERROR_CLASS, exception.getClass().getName());
            response.addHeader(X_CHECKPOINT_FAILURE_ERROR_REASON, verificationException.getHeaderValue());
        }
        response.setStatus(statusCode);
        response.setContentType(APPLICATION_JSON.toString());
        response.getWriter().write(stackTrace);
    }
}
