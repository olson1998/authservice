package com.olson1998.authservice.domain.port.data.repository;

public interface RoleRepository {

    boolean isTimestampTopical(String roleId, long timestamp);
}
