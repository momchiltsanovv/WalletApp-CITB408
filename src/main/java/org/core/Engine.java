package org.core;

import org.exceptions.UserOperationException;
import org.exceptions.WalletOperationException;
import org.services.UserService;
import org.services.WalletService;
import org.services.impl.UserServiceImpl;
import org.services.impl.WalletServiceImpl;

import java.util.Arrays;
import java.util.Currency;
import java.util.Scanner;
import java.util.UUID;

public class Engine implements Runnable {

    private final Scanner scanner;
    private final UserService userService;
    private final WalletService walletService;

    public Engine() {
        this.scanner = new Scanner(System.in);
        UserSessionManager sessionManager = new UserSessionManager();
        this.userService = new UserServiceImpl(sessionManager);
        this.walletService = new WalletServiceImpl(sessionManager);
    }

    @Override
    public void run() {

        System.out.println("Welcome to Smart Wallet!");

        while (true) {
            String result;
            try {
                result = processInput();
                if (result.equals("Exit")) {
                    System.out.println("Goodbye!");
                    break;
                }
                System.out.println("\033[0;34m" + result + "\033[0m");
            } catch (UserOperationException | WalletOperationException e) {
                System.err.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + (e.getMessage() != null ? e.getMessage() : "unknown"));
            }
        }
    }


    private String processInput() {

        String result = null;

        String[] tokens = scanner.nextLine().split("\\s+");
        Command command = Command.valueOf(tokens[0]);

        String[] data = Arrays.stream(tokens)
                              .skip(1)
                              .toArray(String[]::new);


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
