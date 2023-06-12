package com.olson1998.authdata.application.developer.utils;

import com.olson1998.authdata.application.requesting.model.payload.UserMembershipForm;
import com.olson1998.authdata.domain.port.data.utils.SecretAlgorithm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.*;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.datasource.entity.values.RoleSubject.COMPANY;
import static com.olson1998.authdata.application.datasource.entity.values.RoleSubject.PRIVATE;
import static com.olson1998.authdata.application.datasource.entity.values.SecretDigest.SHA_3_256;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SampleDeveloperData {

    protected static final UserSavingRequest SAMPLE_USER_SAVE_REQ = new UserSavingRequest() {
        @Override
        public UserDetails getUserDetails() {
            return new UserDetails() {
                @Override
                public String getUsername() {
                    return "user";
                }

                @Override
                public String getPassword() {
                    return "pass";
                }

                @Override
                public SecretAlgorithm getSecretDigestAlgorithm() {
                    return SHA_3_256;
                }
            };
        }

        @Override
        public Set<UserMembershipClaim> getMembershipClaims() {
            return null;
        }

        @Override
        public UUID getId() {
            return UUID.randomUUID();
        }
    };

    protected static RoleSavingRequest roleSavingRequest(long userId){
        return new RoleSavingRequest() {
            @Override
            public Set<RoleDetails> getDetails() {
                return Set.of(
                        new RoleDetails() {
                            @Override
                            public String getName() {
                                return "DEVELOPER_ROLE_1";
                            }

                            @Override
                            public String getSubject() {
                                return PRIVATE.name();
                            }

                            @Override
                            public Long getUserId() {
                                return userId;
                            }

                            @Override
                            public Long getCompanyNumber() {
                                return null;
                            }

                            @Override
                            public String getRegionId() {
                                return null;
                            }

                            @Override
                            public Long getGroupId() {
                                return null;
                            }

                            @Override
                            public Long getTeamId() {
                                return null;
                            }
                        },
                        new RoleDetails() {
                            @Override
                            public String getName() {
                                return "DEVELOPER_ROLE_2";
                            }

                            @Override
                            public String getSubject() {
                                return COMPANY.name();
                            }

                            @Override
                            public Long getUserId() {
                                return userId;
                            }

                            @Override
                            public Long getCompanyNumber() {
                                return 1L;
                            }

                            @Override
                            public String getRegionId() {
                                return null;
                            }

                            @Override
                            public Long getGroupId() {
                                return null;
                            }

                            @Override
                            public Long getTeamId() {
                                return null;
                            }
                        }
                );
            }

            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }
        };
    }

    protected static AuthoritySavingRequest authoritySavingRequest(){
        return new AuthoritySavingRequest() {
            @Override
            public Set<AuthorityDetails> getAuthoritiesDetails() {
                return Set.of(
                        new AuthorityDetails() {
                            @Override
                            public String getName() {
                                return "AUTHDATA_SERVICE_AUTHORITY_1";
                            }

                            @Override
                            public String getToken() {
                                return null;
                            }

                            @Override
                            public Integer getLevel() {
                                return null;
                            }

                            @Override
                            public Long getExpiringTime() {
                                return null;
                            }

                            @Override
                            public boolean equals(AuthorityDetails authorityDetails) {
                                return false;
                            }
                        },
                        new AuthorityDetails() {
                            @Override
                            public String getName() {
                                return "AUTHDATA_SERVICE_AUTHORITY_2";
                            }

                            @Override
                            public String getToken() {
                                return null;
                            }

                            @Override
                            public Integer getLevel() {
                                return null;
                            }

                            @Override
                            public Long getExpiringTime() {
                                return null;
                            }

                            @Override
                            public boolean equals(AuthorityDetails authorityDetails) {
                                return false;
                            }
                        }
                );
            }

            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }
        };
    }

    protected static UserMembershipSavingRequest userMembershipSavingRequest(long userId){
        return new UserMembershipSavingRequest() {
            @Override
            public long getUserId() {
                return userId;
            }

            @Override
            public Set<UserMembershipClaim> getUserMembershipClaims() {
                return Set.of(
                        new UserMembershipForm(1L, null, null, null)
                );
            }

            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }
        };
    }

    protected static RoleBoundSavingRequest roleBoundSavingRequest(Set<RoleBindingClaim> roleBindingClaims){
        return new RoleBoundSavingRequest() {
            @Override
            public Set<RoleBindingClaim> getRolesBindingsClaims() {
                return roleBindingClaims;
            }

            @Override
            public Map<String, Set<AuthorityDetails>> getRoleIdAuthoritySavingRequestMap() {
                return Collections.emptyMap();
            }

            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }
        };
    }
}
