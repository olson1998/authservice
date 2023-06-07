package com.olson1998.authdata.domain.port.processing.report.stereotype;

public interface UserSavingReport extends ProcessingReport{

    long getUserId();

    String getUsername();
}
