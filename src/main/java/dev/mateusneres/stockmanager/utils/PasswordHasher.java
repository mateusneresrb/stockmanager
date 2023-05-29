package dev.mateusneres.stockmanager.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    public static String encodePassword(char[] password, String nonce){
        return BCrypt.withDefaults().hashToString(10, password);
    }

    public static boolean checkPassword(char[] password, String hash, String nonce){
        BCrypt.Result result = BCrypt.verifyer().verify(password, hash);
        return result.verified;
    }

    public static String generateNonce() {
        byte[] nonceBytes = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonceBytes);
        return Base64.getEncoder().encodeToString(nonceBytes);
    }

}

