package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.port.data.utils.PasswordEncryptionType;
import com.olson1998.authdata.domain.port.security.repository.UserPasswordEnigma;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class UserPasswordEncryptionService implements UserPasswordEnigma {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Argon2PasswordEncoder argon2PasswordEncoder;

    @Override
    public String getEncryptedPassword(PasswordEncryptionType encryptionType, String password) {
        if(encryptionType.isBcrypt()){
            return bCryptPasswordEncoder.encode(password);
        } else if (encryptionType.isArgon2()) {
            return argon2PasswordEncoder.encode(password);
        }else {
            throw new IllegalArgumentException("unknown password encryption");
        }
    }
}
