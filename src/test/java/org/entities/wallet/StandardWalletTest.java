package org.entities.wallet;

import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.UUID;

import static org.entities.wallet.WalletStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class StandardWalletTest {

    @Test
    void test_constructorFields() {
        UUID ownerId = UUID.randomUUID();
        String username = "constructorUser";
        Currency currency = Currency.getInstance("BGN");

        Wallet wallet = new StandardWallet(ownerId, username, currency);

        assertNotNull(wallet.getId());
        assertEquals(ownerId, wallet.getOwnerId());
        assertEquals(username, wallet.getOwnerUsername());
        assertEquals(currency, wallet.getCurrency());
        assertEquals(20.0, wallet.getBalance());
        assertEquals(ACTIVE, wallet.getStatus());
    }

}