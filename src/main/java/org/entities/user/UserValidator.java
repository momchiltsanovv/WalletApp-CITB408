package entities.user;

import static common.SystemErrors.INVALID_PASSWORD;
import static common.SystemErrors.INVALID_USERNAME;

public class UserValidator {

    public static void validateUsername(String username) {

        if (username.isBlank()
                || username.length() < 5
                || username.chars().noneMatch(Character::isDigit)) {
            throw new IllegalArgumentException(INVALID_USERNAME);
        }
    }

    public static void validatePassword(String password) {

        if (password.isBlank()
                || password.length() != 6
                || password.chars().anyMatch(Character::isLetter)) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
    }
}
