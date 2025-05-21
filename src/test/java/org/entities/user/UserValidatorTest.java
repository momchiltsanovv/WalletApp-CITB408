package org.entities.user;

import org.exceptions.UserOperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserValidatorTest {
    @Test
    void test_validUsername_WhenNameBlank() {
        String name = "";
        assertThrows(UserOperationException.class, () -> new User(name, "123123"));
    }

    @Test
    void test_validUsername_WhenNameLessThan5Chars() {
        String name = "abc";
        assertThrows(UserOperationException.class, () -> new User(name, "123123"));
    }

    @Test
    void test_validUserName_WhenNameHasNoDigits() {
        String name = "momoasdfasdf";
        assertThrows(UserOperationException.class, () -> new User(name, "123123"));
    }

    @Test
    void test_validPassword_WhenPasswordBlank() {
        String password = "";
        String name = "validName1";
        assertThrows(UserOperationException.class, () -> new User("name", password));
    }

    @Test
    void test_validPassword_WhenPasswordLessThan6Chars() {
        String password = "12312";
        String name = "validName1";
        assertThrows(UserOperationException.class, () -> new User(name, password));
    }

    @Test
    void test_validPassword_WhenPasswordMoreThan6Chars() {
        String password = "12312123";
        String name = "validName1";
        assertThrows(UserOperationException.class, () -> new User(name, password));
    }

    @Test
    void test_validPassword_WhenPasswordHasLetter() {
        String password = "1231a2";
        String name = "validName1";
        assertThrows(UserOperationException.class, () -> new User(name, password));
    }
}
