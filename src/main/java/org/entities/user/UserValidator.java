package org.entities.user;

import org.exceptions.UserOperationException;

import static org.common.SystemErrors.INVALID_PASSWORD;
import static org.common.SystemErrors.INVALID_USERNAME;

public class UserValidator {

    public static void validateUsername(String username) {

        if (username.isBlank()
                || username.length() < 5
                || username.chars().noneMatch(Character::isDigit)) {
            throw new UserOperationException(INVALID_USERNAME);

        }
    }

    public static void validatePassword(String password) {

        if (password.isBlank()
                || password.length() != 6
                || password.chars().anyMatch(Character::isLetter)) {
            throw new UserOperationException(INVALID_PASSWORD);
        }
    }

}
