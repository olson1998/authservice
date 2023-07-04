package com.olson1998.authdata.application.rest;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.entry;

@ControllerAdvice
public class RestExceptionAdvisor extends ResponseEntityExceptionHandler {

    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

    private static final String ROLLBACK_REQUIRED_EXC_HEADER = "X-Rollback-Required-Exception";

    private static final String PIPELINE_EXCEPTION = "X-Pipeline-Exception";

    private static final String SQL_EXCEPTION = "X-Sql-Exception";

    @ExceptionHandler(value = {RollbackRequiredException.class})
    public ResponseEntity<Map<String, String>> handleRollbackRequiredException(WebRequest webRequest, RollbackRequiredException rollbackRequiredException){
        var rollbackReason = rollbackRequiredException.getClass().getName();
        var log = rollbackRequiredException.getServiceLogger();
        rollbackRequiredException.setStackTrace(EMPTY_STACK_TRACE);
        log.error("Rollback required: ", rollbackRequiredException);
        var responseEntity = ResponseEntity.internalServerError();
        responseEntity.header(ROLLBACK_REQUIRED_EXC_HEADER, rollbackReason);
        var body = createSimpleExceptionObject(rollbackReason, rollbackRequiredException.getMessage());
        return responseEntity.body(body);
    }

    @ExceptionHandler(value = {PipelineRuntimeException.class})
    public ResponseEntity<Map<String, String>> handlePipelineRuntimeException(WebRequest webRequest, PipelineRuntimeException pipelineRuntimeException){
        var failureReason = pipelineRuntimeException.getClass().getName();
        var log = pipelineRuntimeException.getServiceLogger();
        pipelineRuntimeException.setStackTrace(EMPTY_STACK_TRACE);
        log.error("Pipeline failed: ", pipelineRuntimeException);
        var responseEntity = ResponseEntity.internalServerError();
        responseEntity.header(PIPELINE_EXCEPTION, failureReason);
        return responseEntity.body(createSimpleExceptionObject(failureReason, pipelineRuntimeException.getMessage()));
    }

    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<Map<String, Object>> handleSqlException(WebRequest webRequest, SQLException sqlException){
        var failureReason = sqlException.getClass().getName();
        var responseEntity = ResponseEntity.internalServerError();
        responseEntity.header(SQL_EXCEPTION, failureReason);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", failureReason);
        body.put("error_code", sqlException.getErrorCode());
        body.put("sql_state", sqlException.getSQLState());
        return responseEntity.body(body);
    }

    private Map<String, String> createSimpleExceptionObject(String exceptionName, String reason){
        return Map.ofEntries(
                entry("exception", exceptionName),
                entry("message", reason)
        );
    }
}
