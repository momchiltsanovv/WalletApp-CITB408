package org.entities.wallet;

import org.exceptions.WalletOperationException;
import org.utils.SerializationUtils;

import java.io.Serializable;
import java.util.Currency;
import java.util.UUID;

import static org.common.SystemErrors.INSUFFICIENT_FUNDS_IN_WALLET;
import static org.common.SystemErrors.NO_OPERATIONS_ALLOWED_FOR_NON_ACTIVE_WALLET;

public abstract class Wallet implements Serializable {

    private final UUID id;
    private final UUID ownerId;
    private final String ownerUsername;
    private final Currency currency;
    private double balance;
    private WalletStatus status;

    public Wallet(UUID ownerId, String ownerUsername, Currency currency, double balance) {
        this.id = UUID.randomUUID();
        this.ownerId = ownerId;
        this.ownerUsername = ownerUsername;
        this.currency = currency;
        this.balance = balance;
        this.status = WalletStatus.ACTIVE;
    }

    public void deposit(double amount) {

        if (status == WalletStatus.INACTIVE || amount <= 0) throw new WalletOperationException(NO_OPERATIONS_ALLOWED_FOR_NON_ACTIVE_WALLET);

        this.balance += amount;
    }

    public void withdraw(double amount) {

        if (status == WalletStatus.INACTIVE || amount <= 0) throw new WalletOperationException(NO_OPERATIONS_ALLOWED_FOR_NON_ACTIVE_WALLET);

        if (balance < amount) throw new WalletOperationException(INSUFFICIENT_FUNDS_IN_WALLET);

        this.balance -= amount;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public WalletStatus getStatus() {
        return status;
    }

    public void setStatus(WalletStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Wallet [%s][%s]:".formatted(id, this.getClass().getSimpleName()) + System.lineSeparator() +
                "Owner: " + ownerUsername + System.lineSeparator() +
                "Currency: " + currency + System.lineSeparator() +
                "Balance: " + balance + System.lineSeparator() +
                "Status: " + status;
    }
}
