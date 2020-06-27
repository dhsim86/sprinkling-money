package com.kakaopay.sprinkling_money.common.secure;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomGenerator {

    private static final String INUSE_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private SecureRandom secureRandom;

    public SecureRandomGenerator() {
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int generateRandomNumber(int max) {
        return secureRandom.nextInt(max);
    }

    public String generateRandomString(int needStringLength) {
        return secureRandom.ints(needStringLength, 0, INUSE_CHARACTERS.length())
                .mapToObj(INUSE_CHARACTERS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

}
