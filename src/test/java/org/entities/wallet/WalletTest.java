package org.entities.wallet;

import org.exceptions.WalletOperationException;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {


    @Test
    void test_ConstructorInitializesFields() {
        UUID ownerId = UUID.randomUUID();
        String username = "testUser";
        Currency currency = Currency.getInstance("BGN");

        StandardWallet wallet = new StandardWallet(ownerId, username, currency);

        assertNotNull(wallet.getId());
        assertEquals(ownerId, wallet.getOwnerId());
        assertEquals(username, wallet.getOwnerUsername());
        assertEquals(currency, wallet.getCurrency());
        assertEquals(20.0, wallet.getBalance(), 0.0001);
        assertEquals(WalletStatus.ACTIVE, wallet.getStatus());
    }

    @Test
    void test_depositWhenStatusInactive() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        wallet.setStatus(WalletStatus.INACTIVE);
        assertThrows(WalletOperationException.class, () -> wallet.deposit(100.0));

    }
    @Test
    void test_depositWhenAmountLessThan0_ThenThrowsWalletOperationException() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        assertThrows(WalletOperationException.class, () -> wallet.deposit(-10));
    } @Test
    void test_depositWhenAmountIs0_ThenThrowsWalletOperationException() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        assertThrows(WalletOperationException.class, () -> wallet.deposit(0));
    }
    @Test
    void test_withdrawWhenStatusInactive_ThenThrowsWalletOperationException() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        wallet.setStatus(WalletStatus.INACTIVE);
        assertThrows(WalletOperationException.class, () -> wallet.withdraw(100.0));
    }

    @Test
    void test_withdrawWhenAmountLessThan0_ThenThrowsWalletOperationException() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        assertThrows(WalletOperationException.class, () -> wallet.withdraw(-10));
    }
    @Test
    void test_withdrawWhenAmountIs0_ThenThrowsWalletOperationException() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        assertThrows(WalletOperationException.class, () -> wallet.withdraw(0));
    }

    @Test
    void test_withdrawWhenBalanceLessThanAmount_ThenThrowsWalletOperationException() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        wallet.setBalance(50.0);
        assertThrows(WalletOperationException.class, () -> wallet.withdraw(100.0));
    }
    @Test
    void test_getStatusReturnTrueWhenActive() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        wallet.setStatus(WalletStatus.ACTIVE);
        assertSame(WalletStatus.ACTIVE, wallet.getStatus());
    }

    @Test
    void test_IdIsUniquePerInstance() {
        StandardWallet w1 = new StandardWallet(UUID.randomUUID(), "u1", Currency.getInstance("BGN"));
        StandardWallet w2 = new StandardWallet(UUID.randomUUID(), "u2", Currency.getInstance("BGN"));
        assertNotEquals(w1.getId(), w2.getId());
    }

    @Test
    void test_ToStringFormatLayout() {
        UUID ownerId = UUID.randomUUID();
        String ownerUsername = "myName";
        Currency currency = Currency.getInstance("USD");
        StandardWallet wallet = new StandardWallet(ownerId, ownerUsername, currency);

        String result = wallet.toString();

        String[] lines = result.split(System.lineSeparator());

        assertTrue(lines[0].matches("^Wallet \\[.+]\\[StandardWallet]:$"));
        assertEquals("Owner: " + ownerUsername, lines[1]);
        assertEquals("Currency: " + currency, lines[2]);
        assertTrue(lines[3].startsWith("Balance: "));
        assertTrue(lines[4].startsWith("Status: "));
    }



}



