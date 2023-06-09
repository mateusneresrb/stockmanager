package dev.mateusneres.stockmanager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for validating email addresses.
 */
public class EmailValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * This method is responsible for validating email addresses.
     * @param email String
     * @return boolean
     */
    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}
