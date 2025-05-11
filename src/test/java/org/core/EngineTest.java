package org.core;

import org.entities.user.User;
import org.exceptions.UserOperationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.services.UserService;
import org.services.WalletService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO:
class EngineTest {
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testRegister_Login_Exit() {
        String commands = String.join(System.lineSeparator(),
                                      "Register user11 123456",
                                      "Login user11 123456",
                                      "Exit"
                                     );
        System.setIn(new ByteArrayInputStream(commands.getBytes(StandardCharsets.UTF_8)));
        Engine engine = new TestableEngine(new DummyUserService(), new DummyWalletService(), System.in);

        engine.run();

        String output = outputStream.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("[LOG] New user \"user11\" registered successfully."));
        assertTrue(output.contains("[LOG] User user11 successfully logged in."));
        assertTrue(output.contains("Welcome to Smart Wallet!"));
        assertTrue(output.contains("Goodbye!"));
    }

    @Test
    void testUnknownCommandError() {
        String commands = String.join(System.lineSeparator(),
                                      "NonexistentCommand",
                                      "Exit"
                                     );
        System.setIn(new ByteArrayInputStream(commands.getBytes(StandardCharsets.UTF_8)));
        Engine engine = new TestableEngine(new DummyUserService(), new DummyWalletService(), System.in);

        engine.run();

        String output = outputStream.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Input error: Unknown command: NonexistentCommand"));
    }



    static class DummyUserService implements UserService {
        @Override
        public String login(String username, String password) {
            if (username.equals("user11") && password.equals("123456"))
                return "[LOG] User user11 successfully logged in.";
            throw new UserOperationException("Login failed!");
        }

        @Override
        public String register(String username, String password) {
            if (username.equals("user11"))
                return "[LOG] New user \"user11\" registered successfully.";
            throw new UserOperationException("Register failed!");
        }

        @Override
        public String logout() {
            return "[LOG] User user11 successfully logged out.";
        }

        @Override
        public List<User> getAllUsers() {
            return Collections.emptyList();
        }
    }

    static class DummyWalletService implements WalletService {
        @Override
        public String createNewWallet(java.util.Currency c, String type) {
            return "[LOG] New wallet created: " + type + " - " + c.getCurrencyCode();
        }

        @Override
        public String getMyWallets() {
            return "[LOG] No wallets found. You may create one.";
        }

        @Override
        public String deposit(java.util.UUID id, double a) {
            return "[LOG] Deposit successful! Your new balance is: 100.00 BGN.";
        }

        @Override
        public String transfer(java.util.UUID id, String r, double a) {
            return "[LOG] transfer simulated";
        }

        @Override
        public String changeWalletStatus(java.util.UUID id, String s) {
            return "[LOG] Wallet status successfully changed to " + s;
        }
    }

    static class TestableEngine extends Engine {
        public TestableEngine(UserService userSvc, WalletService walletSvc, InputStream in) {
            super(); // call the default constructor (initializes unused real services)
            // Now set private fields via reflection
            try {
                var s = Engine.class.getDeclaredField("scanner");
                var us = Engine.class.getDeclaredField("userService");
                var ws = Engine.class.getDeclaredField("walletService");
                s.setAccessible(true);
                us.setAccessible(true);
                ws.setAccessible(true);
                s.set(this, new Scanner(in));
                us.set(this, userSvc);
                ws.set(this, walletSvc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
