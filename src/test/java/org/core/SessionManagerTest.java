package org.core;

import org.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {
    private UserSessionManager manager;
    private User user;

    @BeforeEach
    void setUp() {
        manager = new UserSessionManager();
        user = new User("test5", "123456");
    }

    @Test
    void testSessionInitiallyNull() {
        assertFalse(manager.hasActiveSession());
        assertNull(manager.getActiveSession());
    }

    @Test
    void testSetActiveSession() {
        manager.setActiveSession(user);
        assertTrue(manager.hasActiveSession());
        assertEquals(user, manager.getActiveSession());
    }

    @Test
    void testTerminateActiveSession() {
        manager.setActiveSession(user);
        manager.terminateActiveSession();
        assertFalse(manager.hasActiveSession());
        assertNull(manager.getActiveSession());
    }
}