package org.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testAllCommandEnumsExist() {
        assertNotNull(Command.valueOf("Login"));
        assertNotNull(Command.valueOf("Register"));
        assertNotNull(Command.valueOf("Logout"));
        assertNotNull(Command.valueOf("NewWallet"));
        assertNotNull(Command.valueOf("MyWallets"));
        assertNotNull(Command.valueOf("ChangeWalletStatus"));
        assertNotNull(Command.valueOf("Deposit"));
        assertNotNull(Command.valueOf("Transfer"));
        assertNotNull(Command.valueOf("Exit"));
    }

    @Test
    void testEnumToStringAndOrdinal() {
        assertEquals("Login", Command.Login.name());
        assertEquals(0, Command.Login.ordinal());
        assertEquals("Exit", Command.Exit.name());
        assertEquals(8, Command.Exit.ordinal());
    }

    @Test
    void testValuesContainsAllEnums() {
        Command[] values = Command.values();
        assertEquals(9, values.length);

        String[] expected = {
                "Login",
                "Register",
                "Logout",
                "NewWallet",
                "MyWallets",
                "ChangeWalletStatus",
                "Deposit",
                "Transfer",
                "Exit"
        };
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], values[i].name());
        }
    }

    @Test
    void testInvalidValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Command.valueOf("NonexistentCommand"));
    }
}
