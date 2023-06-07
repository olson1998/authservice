package com.olson1998.authdata.application.datasource.repository.wrapper;

import com.olson1998.authdata.application.datasource.entity.UserMembershipData;
import com.olson1998.authdata.application.datasource.repository.jpa.UserMembershipJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMembershipJpaRepositoryWrapper implements UserMembershipDataSourceRepository {

    private final UserMembershipJpaRepository userMembershipJpaRepository;

    @Override
    public int deleteUserMembership(long userId) {
        return userMembershipJpaRepository.deleteUserMembership(userId);
    }

    @Override
    public List<UserMembership> saveUserMemberships(Long userId, Set<UserMembershipClaim> claims) {
        var dataSet = createMembershipDataSet(userId, claims);
        var persistedData = userMembershipJpaRepository.saveAll(dataSet);
        return castUserMembershipDataList(persistedData);
    }

    private Set<UserMembershipData> createMembershipDataSet(@NonNull Long userId, @NonNull Set<UserMembershipClaim> claims){
        return claims.stream()
                .map(claim -> new UserMembershipData(userId, claim))
                .collect(Collectors.toUnmodifiableSet());
    }

    private List<UserMembership> castUserMembershipDataList(List<UserMembershipData> userMembershipDataList){
        return userMembershipDataList.stream()
                .map(this::castUserMembership)
                .toList();
    }

    private UserMembership castUserMembership(UserMembershipData userMembershipData){
        return userMembershipData;
    }
}
