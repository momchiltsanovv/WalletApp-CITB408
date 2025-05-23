package org.services.impl;

import org.core.UserSessionManager;
import org.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.repositories.UserRepository;

import java.lang.reflect.Field;
import java.util.List;

import static org.common.LogMessages.*;
import static org.common.SystemErrors.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserSessionManager sessionManager;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        sessionManager = new UserSessionManager();
        userService = new UserServiceImpl(sessionManager);
        ((UserRepository) getActualRepo(userService)).clear();

    }

    private Object getActualRepo(Object service) {
        try {
            Field field = service.getClass().getDeclaredField("userRepository");
            field.setAccessible(true);
            return field.get(service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test_RegisterSuccess() {
        String msg = userService.register("TestUser1", "123456");
        assertEquals(SUCCESSFULLY_REGISTERED.formatted("TestUser1"), msg);

        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("TestUser1", users.getFirst().getUsername());
    }

    @Test
    void test_RegisterDuplicateUsernameThrows() {
        userService.register("TestUser1", "123456");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register("TestUser1", "654321")
                                                  );
        assertEquals(SUCH_USERNAME_ALREADY_EXIST.formatted("TestUser1"), ex.getMessage());
    }

    @Test
    void test_LoginSuccessAndSessionActive() {
        userService.register("TestUser2", "123456");
        String msg = userService.login("TestUser2", "123456");
        assertEquals(SUCCESSFULLY_LOGGED_IN.formatted("TestUser2"), msg);

        assertTrue(sessionManager.hasActiveSession());
        assertEquals("TestUser2", sessionManager.getActiveSession().getUsername());
    }

    @Test
    void test_LoginWrongPasswordThrows() {
        userService.register("WrongPassTestUser1", "123456");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.login("WrongPassTestUser1", "000000")
                                                  );
        assertEquals(INCORRECT_LOGIN_CREDENTIALS, ex.getMessage());
    }

    @Test
    void test_LoginWithActiveSessionThrows() {
        userService.register("ActiveSessTestUser1", "123456");
        userService.login("ActiveSessTestUser1", "123456");
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> userService.login("ActiveSessTestUser1", "123456")
                                               );
        assertEquals(USER_ALREADY_LOGGED_IN.formatted("ActiveSessTestUser1"), ex.getMessage());
    }

    @Test
    void test_LogoutSuccess() {
        userService.register("LogOutTestUser1", "123456");
        userService.login("LogOutTestUser1", "123456");
        String msg = userService.logout();
        assertEquals(SUCCESSFULLY_LOGGED_OUT.formatted("LogOutTestUser1"), msg);
        assertFalse(sessionManager.hasActiveSession());
    }

    @Test
    void test_LogoutWithoutActiveSessionThrows() {
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> userService.logout()
                                               );
        assertEquals(NO_ACTIVE_USER_SESSION_FOUND, ex.getMessage());
    }

    @Test
    void test_GetAllUsersReturnsCorrectUsers() {
        userService.register("TestUser1", "222222");
        userService.register("TestUser2", "333333");
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.stream()
                        .anyMatch(u -> u.getUsername().equals("TestUser1")));

        assertTrue(users.stream()
                        .anyMatch(u -> u.getUsername().equals("TestUser2")));
    }
}