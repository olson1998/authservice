package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.repository.jpa.RoleJpaRepository;
import com.olson1998.authservice.domain.port.data.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleJpaRepositoryWrapper implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public boolean isTimestampTopical(String roleId, long timestamp) {
        return roleJpaRepository.selectCaseIfTimestampIsTopical(roleId, timestamp, System.currentTimeMillis());
    }
}
