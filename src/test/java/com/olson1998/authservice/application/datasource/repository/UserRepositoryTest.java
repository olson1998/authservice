package com.olson1998.authservice.application.datasource.repository;

import com.olson1998.authservice.application.datasource.entity.independent.UserEntity;
import com.olson1998.authservice.application.datasource.repository.jpa.independent.UserJpaRepository;
import com.olson1998.authservice.application.datasource.repository.wrapper.independet.UserJpaRepositoryWrapper;
import com.olson1998.authservice.domain.model.auth.data.UserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    private static final String TEST_USERNAME = "user";

    @Mock
    private UserEntity userEntity;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserJpaRepositoryWrapper userJpaRepositoryWrapper;

    @Test
    void shouldSaveUser(){
        try(var userEntityClassMock = Mockito.mockStatic(UserEntity.class)){
            userEntityClassMock.when(() ->UserEntity.fromUserDetails(userDetails))
                    .thenReturn(userEntity);

            userJpaRepositoryWrapper.saveUser(userDetails);

            then(userJpaRepository).should().save(userEntity);
        }
    }

    @Test
    void shouldGetUserByUsername(){
        userJpaRepositoryWrapper.getUser(TEST_USERNAME);

        then(userJpaRepository).should().getUserByUsername(TEST_USERNAME);
    }
}
