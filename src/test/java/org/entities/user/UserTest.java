package org.entities.user;

import org.exceptions.UserOperationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void test_CreateUserSuccess() {
        User user = new User("test5", "123456");
        assertNotNull(user.getId());
        assertEquals("test5", user.getUsername());
        assertEquals("123456", user.getPassword());
    }

    @Test
    void test_UniqueIdsForDifferentUsers() {
        User u1 = new User("user10", "222222");
        User u2 = new User("user11", "333333");
        assertNotEquals(u1.getId(), u2.getId());
    }

    @Test
    void test_InvalidUsernameThrowsException() {
        assertThrows(UserOperationException.class, () -> new User("abc", "123456"));
        assertThrows(UserOperationException.class, () -> new User("nodigits", "123456"));
        assertThrows(UserOperationException.class, () -> new User("", "123456"));
        assertThrows(UserOperationException.class, () -> new User("    ", "123456"));
    }

    @Test
    void test_InvalidPasswordThrowsException() {
        assertThrows(UserOperationException.class, () -> new User("user5", "abc123"));
        assertThrows(UserOperationException.class, () -> new User("user5", "12345"));
        assertThrows(UserOperationException.class, () -> new User("user5", "1234567"));
        assertThrows(UserOperationException.class, () -> new User("user5", ""));
    }

    @Test
    void test_SettersValidate() {
        User user = new User("test5", "123456");
        assertEquals("test5", user.getUsername());
        assertEquals("123456", user.getPassword());
    }

    @Test
    void test_GetUsernameAndPassword() {
        User user = new User("abc12", "666666");
        assertEquals("abc12", user.getUsername());
        assertEquals("666666", user.getPassword());
        assertInstanceOf(UUID.class, user.getId());
    }
}
