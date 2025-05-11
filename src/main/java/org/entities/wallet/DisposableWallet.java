package org.entities.wallet;

import java.util.Currency;
import java.util.UUID;

import static org.common.SystemErrors.WITHDRAWAL_LIMIT_REACHED_FOR_DISPOSABLE_WALLET;

public class DisposableWallet extends Wallet {

    private int withdrawAttempts;

    public DisposableWallet(UUID ownerId, String ownerUsername, Currency currency) {
        super(ownerId, ownerUsername, currency, 0.00);
    }

    @Override
    public void withdraw(double amount) {

        if (withdrawAttempts == 2) {
            setStatus(WalletStatus.INACTIVE);
            throw new IllegalStateException(WITHDRAWAL_LIMIT_REACHED_FOR_DISPOSABLE_WALLET);
        }
        super.withdraw(amount);
        this.withdrawAttempts += 1;
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() +
                "Max withdrawals: " + 2 + System.lineSeparator() +
                "Current withdrawals: " + withdrawAttempts;
    }
}
