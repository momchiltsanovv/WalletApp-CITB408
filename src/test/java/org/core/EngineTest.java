package org.core;

import org.junit.jupiter.api.*;
import org.services.UserService;
import org.services.WalletService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EngineTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;

    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(errContent, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    static void inject(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRegisterLoginExit_withMocks() {
        String commands = String.join(System.lineSeparator(),
                                      "Register alice1 123456",
                                      "Login alice1 123456",
                                      "Exit"
                                     );
        System.setIn(new ByteArrayInputStream(commands.getBytes(StandardCharsets.UTF_8)));

        UserService userService = mock(UserService.class);
        WalletService walletService = mock(WalletService.class);

        when(userService.register("alice1", "123456")).thenReturn("[LOG] New user \"alice1\" registered successfully.");
        when(userService.login("alice1", "123456")).thenReturn("[LOG] User alice1 successfully logged in.");

        Engine engine = new Engine();
        inject(engine, "userService", userService);
        inject(engine, "walletService", walletService);
        inject(engine, "scanner", new Scanner(System.in));

        engine.run();

        String output = outContent.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Welcome to Smart Wallet!"));
        assertTrue(output.contains("[LOG] New user \"alice1\" registered successfully."));
        assertTrue(output.contains("[LOG] User alice1 successfully logged in."));
        assertTrue(output.contains("Goodbye!"));

        verify(userService, times(1)).register("alice1", "123456");
        verify(userService, times(1)).login("alice1", "123456");
        verifyNoInteractions(walletService);
    }

    @Test
    void testUnknownCommand_showsError() {
        String commands = String.join(System.lineSeparator(),
                                      "FakeCommand",
                                      "Exit"
                                     );
        System.setIn(new ByteArrayInputStream(commands.getBytes(StandardCharsets.UTF_8)));

        UserService userService = mock(UserService.class);
        WalletService walletService = mock(WalletService.class);

        Engine engine = new Engine();
        inject(engine, "userService", userService);
        inject(engine, "walletService", walletService);
        inject(engine, "scanner", new Scanner(System.in));

        engine.run();

        String errOutput = errContent.toString(StandardCharsets.UTF_8);
        assertTrue(errOutput.contains("Input error: Unknown command: FakeCommand"));
    }

    @Test
    void testDeposit_invokesWalletService() {
        String fakeWalletId = "00000000-0000-0000-0000-000000000001";
        String commands = String.join(System.lineSeparator(),
                                      "Deposit " + fakeWalletId + " 45.67",
                                      "Exit"
                                     );
        System.setIn(new ByteArrayInputStream(commands.getBytes(StandardCharsets.UTF_8)));

        UserService userService = mock(UserService.class);
        WalletService walletService = mock(WalletService.class);
        when(walletService.deposit(UUID.fromString(fakeWalletId), 45.67))
                .thenReturn("[LOG] Deposit successful!");

        Engine engine = new Engine();
        inject(engine, "userService", userService);
        inject(engine, "walletService", walletService);
        inject(engine, "scanner", new Scanner(System.in));

        engine.run();

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("[LOG] Deposit successful!"));

        verify(walletService, times(1)).deposit(UUID.fromString(fakeWalletId), 45.67);
    }
}
