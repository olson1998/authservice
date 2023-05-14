package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.UserMembershipData;
import com.olson1998.authservice.application.datasource.repository.jpa.UserMembershipJpaRepository;
import com.olson1998.authservice.domain.port.data.repository.UserMembershipRepository;
import com.olson1998.authservice.domain.port.request.entity.UserMembershipClaim;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserMembershipJpaRepositoryWrapper implements UserMembershipRepository {

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
        var dataSet = new HashSet<UserMembershipData>();
        claims.forEach(claim ->{
            if(claim != null){
                dataSet.add(new UserMembershipData(claim));
            }
        });
        return dataSet;
    }
}