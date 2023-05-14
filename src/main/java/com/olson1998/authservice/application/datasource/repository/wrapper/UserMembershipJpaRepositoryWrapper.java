package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.UserMembershipData;
import com.olson1998.authservice.application.datasource.repository.jpa.UserMembershipJpaRepository;
import com.olson1998.authservice.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authservice.domain.port.request.entity.UserMembershipClaim;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMembershipJpaRepositoryWrapper implements UserMembershipDataSourceRepository {

    private final UserMembershipJpaRepository userMembershipJpaRepository;

    @Override
    @Transactional
    public int deleteUserMembership(long userId) {
        return userMembershipJpaRepository.deleteUserMembership(userId);
    }

    @Override
    @Transactional
    public void saveUserMemberships(Set<UserMembershipClaim> claims) {
        var dataSet = createMembershipDataSet(claims);
        userMembershipJpaRepository.saveAll(dataSet);
    }

    private Set<UserMembershipData> createMembershipDataSet(@NonNull Set<UserMembershipClaim> claims){
        return claims.stream()
                .map(UserMembershipData::new)
                .collect(Collectors.toUnmodifiableSet());
    }
}
