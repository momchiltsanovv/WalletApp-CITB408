package org.entities.user;

import org.exceptions.UserOperationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testCreateUserSuccess() {
        User user = new User("test5", "123456");
        assertNotNull(user.getId());
        assertEquals("test5", user.getUsername());
        assertEquals("123456", user.getPassword());
    }

    @Test
    void testUniqueIdsForDifferentUsers() {
        User u1 = new User("user10", "222222");
        User u2 = new User("user11", "333333");
        assertNotEquals(u1.getId(), u2.getId());
    }

    @Test
    void testInvalidUsernameThrowsException() {
        assertThrows(UserOperationException.class, () -> new User("abc", "123456")); // too short
        assertThrows(UserOperationException.class, () -> new User("nodigits", "123456")); // no digit
        assertThrows(UserOperationException.class, () -> new User("", "123456")); // blank
        assertThrows(UserOperationException.class, () -> new User("    ", "123456")); // blank but with spaces
    }

    @Test
    void testInvalidPasswordThrowsException() {
        assertThrows(UserOperationException.class, () -> new User("user5", "abc123")); // contains letter
        assertThrows(UserOperationException.class, () -> new User("user5", "12345"));  // too short
        assertThrows(UserOperationException.class, () -> new User("user5", "1234567"));  // too long
        assertThrows(UserOperationException.class, () -> new User("user5", ""));  // blank
    }

    @Test
    void testSettersValidate() {
        User user = new User("test5", "123456");
        assertEquals("test5", user.getUsername());
        assertEquals("123456", user.getPassword());
    }

    @Test
    void testGetUsernameAndPassword() {
        User user = new User("abc12", "666666");
        assertEquals("abc12", user.getUsername());
        assertEquals("666666", user.getPassword());
        assertTrue(user.getId() instanceof UUID);
    }
}
