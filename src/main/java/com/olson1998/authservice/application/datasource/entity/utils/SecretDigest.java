package com.olson1998.authservice.application.datasource.entity.utils;

import com.olson1998.authservice.domain.port.data.utils.SecretAlgorithm;
import com.olson1998.authservice.domain.port.data.utils.SecretEncryption;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public enum SecretDigest implements SecretEncryption, SecretAlgorithm {

    NONE,
    MD2,
    MD5,
    SHA1,
    SHA256,
    SHA384,
    SHA512_224,
    SHA512_256,
    SHA_3_224,
    SHA_3_256,
    SHA_3_384,
    SHA_3_512;

    public static final SecretDigest DEFAULT_DIGEST = SHA256;

    /**
     * Method returns Message Digest of Password digest
     * @return resolved message digest
     */
    private MessageDigest toMessageDigest(){
        switch (this){
            case MD2 -> {
                return DigestUtils.getMd2Digest();
            }case MD5 -> {
                return DigestUtils.getMd5Digest();
            }case SHA1 -> {
                return DigestUtils.getSha1Digest();
            }case SHA256 -> {
                return DigestUtils.getSha256Digest();
            }case SHA384 -> {
                return DigestUtils.getSha384Digest();
            }case SHA512_224 -> {
                return DigestUtils.getSha512_224Digest();
            }case SHA512_256 -> {
                return DigestUtils.getSha512_256Digest();
            }case SHA_3_224 -> {
                return DigestUtils.getSha3_224Digest();
            }case SHA_3_256 -> {
                return DigestUtils.getSha3_256Digest();
            }case SHA_3_384 -> {
                return DigestUtils.getSha3_384Digest();
            }case SHA_3_512 -> {
                return DigestUtils.getSha3_512Digest();
            }default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public String getAlgorithm() {
        return this.name();
    }

    @Override
    public String encrypt(@NonNull String password){
        if(!this.equals(NONE)){
            var digest = toMessageDigest();
            var encPassBytes = DigestUtils.digest(digest, password.getBytes(UTF_8));
            return new String(encPassBytes, UTF_8);
        }else {
            return password;
        }
    }

    /**
     * Returns Password digest of user details
     * @param userDetails User details object
     * @return Password digest of user details
     */
    public static SecretDigest ofUserDetails(@NonNull UserDetails userDetails){
        var alg = Objects.
                requireNonNullElse(userDetails.getSecretDigestAlgorithm(), NONE)
                .getAlgorithm();
        try{
            return SecretDigest.valueOf(alg);
        }catch (IllegalArgumentException e){
            log.warn("Could not read password digest, falling to default");
            return DEFAULT_DIGEST;
        }
    }

    public static SecretDigest ofAlgorithm(@NonNull SecretAlgorithm encrypt){
        if(encrypt.getClass().equals(SecretDigest.class)){
            return (SecretDigest) encrypt;
        }else {
            return SecretDigest.valueOf(encrypt.getAlgorithm());
        }
    }
}
