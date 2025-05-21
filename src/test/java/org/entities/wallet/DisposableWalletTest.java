package org.entities.wallet;

import org.exceptions.WalletOperationException;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.UUID;

import static org.common.SystemErrors.WITHDRAWAL_LIMIT_REACHED_FOR_DISPOSABLE_WALLET;
import static org.junit.jupiter.api.Assertions.*;

class DisposableWalletTest {
    @Test
    void test_constructorFields(){
        UUID ownerId = UUID.randomUUID();
        String username = "constructorUser";
        Currency currency = Currency.getInstance("BGN");

        DisposableWallet wallet = new DisposableWallet(ownerId, username, currency);

        assertNotNull(wallet.getId());
        assertEquals(ownerId, wallet.getOwnerId());
        assertEquals(username, wallet.getOwnerUsername());
        assertEquals(currency, wallet.getCurrency());
        assertEquals(0.0, wallet.getBalance());
        assertEquals(WalletStatus.ACTIVE, wallet.getStatus());
    }

    @Test
    void test_withdrawWhenAttemptsEquals2_ThenSetStatusInactive() {
        Wallet w = new DisposableWallet(UUID.randomUUID(),"test", Currency.getInstance("BGN"));
        w.deposit(10);
        w.withdraw(1);
        w.withdraw(1);
        WalletOperationException ex = assertThrows(WalletOperationException.class, () -> w.withdraw(1.0));
        assertEquals("Error: Withdrawal limit reached. This disposable wallet is now inactive and cannot process further transactions.", WITHDRAWAL_LIMIT_REACHED_FOR_DISPOSABLE_WALLET);
        assertEquals(WalletStatus.INACTIVE, w.getStatus());
    }
    @Test
    void testToStringIncludesWithdrawals() {
        DisposableWallet w = new DisposableWallet(UUID.randomUUID(), "user2", Currency.getInstance("BGN"));
        w.deposit(10.0);
        w.withdraw(3.0);
        String str = w.toString();
        assertTrue(str.contains("Current withdrawals: 1"));
        assertTrue(str.contains("Max withdrawals: 2"));
    }
}