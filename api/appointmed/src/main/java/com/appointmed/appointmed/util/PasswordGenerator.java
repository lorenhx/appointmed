package com.appointmed.appointmed.util;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*_=+-/";

    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    private static final Random RANDOM = new SecureRandom();

    public static String getRandomPassword(int length) {
        StringBuilder password = new StringBuilder(length);

        password.append(randomChar(UPPER));
        password.append(randomChar(LOWER));
        password.append(randomChar(DIGITS));
        password.append(randomChar(SPECIAL));

        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL));
        }

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(length);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(randomIndex));
            password.setCharAt(randomIndex, temp);
        }
        return password.toString();
    }

    private static char randomChar(String characterSet) {
        int randomIndex = RANDOM.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }

}