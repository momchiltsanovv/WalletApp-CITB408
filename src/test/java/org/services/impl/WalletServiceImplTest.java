package org.services.impl;

import org.core.UserSessionManager;
import org.entities.user.User;
import org.entities.wallet.StandardWallet;
import org.entities.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.repositories.WalletRepository;

import java.lang.reflect.Field;
import java.util.Currency;
import java.util.UUID;

import static org.common.LogMessages.ZERO_WALLETS;
import static org.common.SystemErrors.*;
import static org.entities.wallet.WalletStatus.INACTIVE;
import static org.junit.jupiter.api.Assertions.*;

class WalletServiceImplTest {
    private UserSessionManager sessionManager;
    private WalletServiceImpl walletService;
    private User user;

    @BeforeEach
    void setUp() {
        sessionManager = new UserSessionManager();
        walletService = new WalletServiceImpl(sessionManager);
        user = new User("userW1", "123456");
        sessionManager.setActiveSession(user);
    }

    @Test
    void test_CreateNewStandardWalletSuccess() {
        String walletInfo = walletService.createNewWallet(Currency.getInstance("BGN"), "Standard");
        assertTrue(walletInfo.contains("StandardWallet"));
        assertTrue(walletInfo.contains("Owner: userW1"));
        assertThrows(IllegalStateException.class, () -> walletService.createNewWallet(Currency.getInstance("BGN"), "Standard"));
    }

    @Test
    void test_CreateNewDisposableWalletSuccess() {
        String walletInfo = walletService.createNewWallet(Currency.getInstance("BGN"), "Disposable");
        assertTrue(walletInfo.contains("DisposableWallet"));
    }

    @Test
    void testCreateNewSavingsWalletSuccess() {
        String walletInfo = walletService.createNewWallet(Currency.getInstance("BGN"), "Savings");
        assertTrue(walletInfo.contains("SavingsWallet"));
    }

    @Test
    void test_CreateWalletWithInvalidTypeThrows() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> walletService.createNewWallet(Currency.getInstance("BGN"), "InvalidType")
                                                  );
        assertEquals(INCORRECT_WALLET_TYPE, ex.getMessage());
    }

    @Test
    void testGetMyWalletsEmpty() {
        sessionManager.terminateActiveSession();
        sessionManager.setActiveSession(user);
        assertEquals(ZERO_WALLETS, walletService.getMyWallets());
    }

    @Test
    void test_GetMyWalletsSingleWallet() {
        walletService.createNewWallet(Currency.getInstance("BGN"), "Disposable");
        String allWallets = walletService.getMyWallets();
        assertTrue(allWallets.contains("DisposableWallet"));
        assertFalse(allWallets.contains("StandardWallet"));
    }

    @Test
    void test_DepositSuccess() {
        walletService.createNewWallet(Currency.getInstance("BGN"), "Standard");
        Wallet wallet = getStandardWalletForActiveUser();
        assertNotNull(wallet);

        String depositMsg = walletService.deposit(wallet.getId(), 10.0);
        assertTrue(depositMsg.contains("Deposit successful!"));
        assertTrue(wallet.getBalance() > 20.0);
    }


    @Test
    void test_DepositWithWrongIdThrows() {
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                                                () -> walletService.deposit(id, 10.0));
        assertTrue(ex.getMessage().contains("User userW1 is not associated with such wallet."),
                   "Should throw if wallet with ID is not associated.");
    }

    @Test
    void test_ChangeWalletStatusSuccess() {
        Wallet wallet = createStandardWalletForSessionUser();
        String msg = walletService.changeWalletStatus(wallet.getId(), "INACTIVE");
        assertTrue(msg.contains("successfully changed to INACTIVE"));
        assertEquals(INACTIVE, wallet.getStatus());
    }

    @Test
    void test_ChangeWalletStatusWithInvalidStatus_ThenThrowsIllegalArgumentException() {
        Wallet wallet = createStandardWalletForSessionUser();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                                                   () -> walletService.changeWalletStatus(wallet.getId(), "INVALID"));
        assertEquals(INCORRECT_WALLET_STATUS, ex.getMessage());
    }

    @Test
    void test_TransferSuccess() {

        Wallet senderWallet = createStandardWalletForSessionUser();
        walletService.deposit(senderWallet.getId(), 50.0);

        User receiver = new User("ReceiverTestUser1", "222222");
        sessionManager.terminateActiveSession();
        sessionManager.setActiveSession(receiver);
        walletService.createNewWallet(Currency.getInstance("BGN"), "Standard");

        sessionManager.terminateActiveSession();
        sessionManager.setActiveSession(user);

        String msg = walletService.transfer(senderWallet.getId(), "ReceiverTestUser1", 15.0);
        assertTrue(msg.contains("transferred 15.00 to ReceiverTestUser1") || msg.contains("transferred 15,00 to ReceiverTestUser1"));
    }

    @Test
    void test_TransferNoReceiverWallet_ThenThrowsIllegalStateException() {
        Wallet senderWallet = createStandardWalletForSessionUser();
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                                                () -> walletService.transfer(senderWallet.getId(), "nosuchuser", 10.0));
        assertTrue(ex.getMessage().contains("Receiver nosuchuser has no Standard wallet"));
    }

    @Test
    void test_CreateWalletWithoutActiveSession_ThenThrowsIllegalStateException() {
        sessionManager.terminateActiveSession();
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                                                () -> walletService.createNewWallet(Currency.getInstance("BGN"), "Standard"));
        assertEquals(NO_ACTIVE_USER_SESSION_FOUND, ex.getMessage());
    }

    private Wallet getStandardWalletForActiveUser() {
        return getRepo()
                .getAll()
                .stream()
                .filter(w -> w.getOwnerUsername().equals(user.getUsername())
                        && w instanceof StandardWallet)
                .findFirst()
                .orElse(null);
    }

    private Wallet createStandardWalletForSessionUser() {
        walletService.createNewWallet(Currency.getInstance("BGN"), "Standard");
        return getRepo()
                .getAll()
                .stream()
                .filter(w -> w.getOwnerUsername().equals(sessionManager.getActiveSession().getUsername())
                        && w instanceof StandardWallet)
                .findFirst()
                .orElseThrow();
    }

    private WalletRepository getRepo() {
        try {
            Field f = WalletServiceImpl.class.getDeclaredField("walletRepository");
            f.setAccessible(true);
            return (WalletRepository) f.get(walletService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
