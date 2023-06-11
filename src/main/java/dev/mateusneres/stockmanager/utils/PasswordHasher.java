package dev.mateusneres.stockmanager.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * This class is responsible for hashing passwords.
 */
public class PasswordHasher {

    /**
     * This method is responsible for hashing passwords.
     * @param password char[]
     * @return String
     */
    public static String encodePassword(char[] password){
        return BCrypt.withDefaults().hashToString(10, password);
    }

    /**
     * This method is responsible for checking if the password is correct.
     * @param password char[]
     * @param hash String
     * @return boolean
     */
    public static boolean checkPassword(char[] password, String hash){
        BCrypt.Result result = BCrypt.verifyer().verify(password, hash);
        return result.verified;
    }

    /**
     * This method is responsible for generating a nonce.
     * @return String
     */
    public static String generateNonce() {
        byte[] nonceBytes = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonceBytes);
        return Base64.getEncoder().encodeToString(nonceBytes);
    }

}

