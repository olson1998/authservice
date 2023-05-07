package com.olson1998.authservice.application.data.entity;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

public enum PasswordDigest {

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

    public MessageDigest toMessageDigest(){
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
}
