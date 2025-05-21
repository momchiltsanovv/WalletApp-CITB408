package org.entities.wallet;

import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SavingsWalletTest {
    @Test
    void test_constructorFields() {
        UUID ownerId = UUID.randomUUID();
        String username = "constructorUser";
        Currency currency = Currency.getInstance("BGN");

        Wallet wallet = new SavingsWallet(ownerId, username, currency);

        assertNotNull(wallet.getId());
        assertEquals(ownerId, wallet.getOwnerId());
        assertEquals(username, wallet.getOwnerUsername());
        assertEquals(currency, wallet.getCurrency());
        assertEquals(10.0, wallet.getBalance());
        assertEquals(WalletStatus.ACTIVE, wallet.getStatus());
    }

    @Test
    void test_withdrawWhenTimeBeforeSavingPeriodEnd_ThenThrowIllegalStateException() {
        Wallet w = new SavingsWallet(UUID.randomUUID(), "test", Currency.getInstance("BGN"));
        w.deposit(20);
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                                                () -> w.withdraw(5));
    }

    @Test
    void test_toString() {
        UUID ownerId = UUID.randomUUID();
        String username = "saverX";
        Currency currency = Currency.getInstance("BGN");
        SavingsWallet wallet = new SavingsWallet(ownerId, username, currency);

        String str = wallet.toString();

        assertTrue(str.contains("Owner: " + username));
        assertTrue(str.contains("Wallet ["));
        assertTrue(str.contains("Status: ACTIVE"));

        assertTrue(str.contains("Saving period ends within: "), "Missing saving period label");

        String[] lines = str.split(System.lineSeparator());
        boolean found = false;
        for (String line : lines) {
            if (line.startsWith("Saving period ends within: ")) {
                String trimmed = line.replace("Saving period ends within: ", "").replace(" seconds", "");
                int seconds = Integer.parseInt(trimmed);
                assertTrue(seconds > 0 && seconds <= 120);
                found = true;
            }
        }
        assertTrue(found, "toString should show saving period in seconds");
    }
}