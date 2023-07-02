package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.application.security.props.PasswordEncodersProperties;
import com.olson1998.authdata.domain.port.security.repository.UserPasswordEnigma;
import com.olson1998.authdata.domain.service.security.UserPasswordEncryptionService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UsersPasswordEncryptorConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder(@NonNull PasswordEncodersProperties passwordEncodersProperties){
        var argon2 = passwordEncodersProperties.getArgon2();
        return new Argon2PasswordEncoder(
                argon2.getSaltLength(),
                argon2.getHashLength(),
                argon2.getParallelism(),
                argon2.getMemory(),
                argon2.getIterations()
        );
    }

    @Bean
    public UserPasswordEnigma userPasswordEnigma(@NonNull BCryptPasswordEncoder bCryptPasswordEncoder,
                                                 @NonNull Argon2PasswordEncoder argon2PasswordEncoder){
        return new UserPasswordEncryptionService(
                bCryptPasswordEncoder,
                argon2PasswordEncoder
        );
    }
}
