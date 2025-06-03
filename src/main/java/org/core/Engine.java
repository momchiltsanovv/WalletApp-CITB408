package org.core;

import org.entities.user.User;
import org.entities.wallet.Wallet;
import org.exceptions.UserOperationException;
import org.exceptions.WalletOperationException;
import org.repositories.UserRepository;
import org.repositories.WalletRepository;
import org.services.UserService;
import org.services.WalletService;
import org.services.impl.UserServiceImpl;
import org.services.impl.WalletServiceImpl;
import org.utils.SerializationUtils;

import java.util.*;

public class Engine implements Runnable {

    private final Scanner scanner;
    private final UserService userService;
    private final WalletService walletService;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;


    public Engine() {
        this.scanner = new Scanner(System.in);
        UserSessionManager sessionManager = new UserSessionManager();
        this.userService = new UserServiceImpl(sessionManager);
        this.walletService = new WalletServiceImpl(sessionManager);


        this.userRepository = extractRepository(userService, "userRepository");
        this.walletRepository = extractRepository(walletService, "walletRepository");
    }

    private void saveAll() {
        try {
            SerializationUtils.serialize(userRepository.getAll(), "users.ser");
            SerializationUtils.serialize(walletRepository.getAll(), "wallets.ser");
        } catch (Exception e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.println("Welcome to Smart Wallet!");

        while (true) {
            String result;
            try {
                result = processInput();
                if (result.equals("Exit")) {
                    saveAll();
                    System.out.println("Goodbye!");
                    break;
                }
                System.out.println("\033[0;34m" + result + "\033[0m");
                saveAll();
            } catch (UserOperationException | WalletOperationException e) {
                System.err.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + (e.getMessage() != null ? e.getMessage() : "unknown"));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T extractRepository(Object service, String fieldName) {
        try {
            var field = service.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String processInput() {

        String result;

        String[] tokens = scanner.nextLine().trim().split("\\s+");
        if (tokens.length == 0 || tokens[0].isBlank()) {
            throw new IllegalArgumentException("No command entered!");
        }
        Command command;
        try {
            command = Command.valueOf(tokens[0]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown command: " + tokens[0]);
        }
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);


        result = switch (command) {
            case Login -> userService.login(data[0], data[1]);
            case Register -> userService.register(data[0], data[1]);
            case Logout -> userService.logout();
            case NewWallet -> walletService.createNewWallet(Currency.getInstance(data[0]), data[1]);
            case MyWallets -> walletService.getMyWallets();
            case ChangeWalletStatus -> walletService.changeWalletStatus(UUID.fromString(data[0]), data[1]);
            case Deposit -> walletService.deposit(UUID.fromString(data[0]), Double.parseDouble(data[1]));
            case Transfer -> walletService.transfer(UUID.fromString(data[0]), data[1], Double.parseDouble(data[2]));
            case Exit -> Command.Exit.name();
        };
        return result;
    }
}
