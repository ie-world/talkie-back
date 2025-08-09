// com/example/talkie/util/RegexValidator.java
package com.example.talkie.util;

import java.util.regex.Pattern;

public class RegexValidator {
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{4,15}$";
    // DTO와 동일한 정책으로 통일
    private static final String PASSWORD_REGEX =
            "^(?=.{8,15}$)(?:(?=.*[A-Za-z])(?=.*\\d)|(?=.*[A-Za-z])(?=.*[!@#$%^&*])|(?=.*\\d)(?=.*[!@#$%^&*])).*$";

    public static boolean isValidUsername(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }
}
