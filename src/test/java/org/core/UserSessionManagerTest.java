package org.core;

import org.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionManagerTest {

    private UserSessionManager sessionManager;
    private User user;

    @BeforeEach
    void setUp() {
        sessionManager = new UserSessionManager();
        user = new User("testuser1", "123456");
    }

    @Test
    void test_initiallyNoActiveSession() {
        assertFalse(sessionManager.hasActiveSession());
        assertNull(sessionManager.getActiveSession());
    }

    @Test
    void test_setAndGetActiveSession() {
        sessionManager.setActiveSession(user);
        assertTrue(sessionManager.hasActiveSession());
        assertEquals(user, sessionManager.getActiveSession());
    }

    @Test
    void test_terminateActiveSession() {
        sessionManager.setActiveSession(user);
        assertTrue(sessionManager.hasActiveSession());
        sessionManager.terminateActiveSession();
        assertFalse(sessionManager.hasActiveSession());
        assertNull(sessionManager.getActiveSession());
    }
}

