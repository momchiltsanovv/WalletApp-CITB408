package org.repositories;

import org.entities.wallet.StandardWallet;
import org.entities.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WalletRepositoryTest {
    private WalletRepository repo;

    @BeforeEach
    void setUp() {
        repo = new WalletRepository();
        repo.clear();
    }

    @Test
    void test_SaveWallet() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        repo.save(wallet.getId(), wallet);

        List<Wallet> allWallets = repo.getAll();
        assertEquals(1, allWallets.size());
        assertTrue(allWallets.contains(wallet));
    }

    @Test
    void test_GetWalletById() {
        StandardWallet wallet = new StandardWallet(UUID.randomUUID(), "user1", Currency.getInstance("BGN"));
        repo.save(wallet.getId(), wallet);

        Wallet found = repo.getById(wallet.getId());
        assertNotNull(found);
        assertEquals(wallet, found);
        assertEquals("user1", found.getOwnerUsername());
    }

    @Test
    void test_GetAllStartsEmpty() {
        assertTrue(repo.getAll().isEmpty());
    }

    @Test
    void test_GetAllReturnsAllWallets() {
        StandardWallet w1 = new StandardWallet(UUID.randomUUID(), "userA", Currency.getInstance("BGN"));
        StandardWallet w2 = new StandardWallet(UUID.randomUUID(), "userB", Currency.getInstance("USD"));
        repo.save(w1.getId(), w1);
        repo.save(w2.getId(), w2);

        List<Wallet> all = repo.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(w1));
        assertTrue(all.contains(w2));
    }

    @Test
    void test_OverwriteWalletById() {
        UUID id = UUID.randomUUID();
        StandardWallet w1 = new StandardWallet(id, "one", Currency.getInstance("BGN"));
        StandardWallet w2 = new StandardWallet(id, "two", Currency.getInstance("BGN"));

        repo.save(id, w1);
        repo.save(id, w2);

        Wallet found = repo.getById(id);
        assertEquals(w2, found);
        assertEquals("two", found.getOwnerUsername());
    }
}
