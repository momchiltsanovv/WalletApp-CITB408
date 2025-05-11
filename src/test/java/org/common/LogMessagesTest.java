package org.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMessagesTest {

    @Test
    void testSuccessfullyRegisteredFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_REGISTERED, "pesho");
        assertEquals("[LOG] New user \"pesho\" registered successfully.", res);
    }

    @Test
    void testSuccessfullyLoggedInFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_LOGGED_IN, "maria");
        assertEquals("[LOG] User maria successfully logged in.", res);
    }

    @Test
    void testSuccessfullyLoggedOutFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_LOGGED_OUT, "gosho");
        assertEquals("[LOG] User gosho successfully logged out.", res);
    }

    @Test
    void testZeroWallets() {
        assertEquals("[LOG] No wallets found. You may create one.", LogMessages.ZERO_WALLETS);
    }

    @Test
    void testSuccessfullyDepositedAmountFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_DEPOSITED_AMOUNT, "100.00", "BGN");
        assertEquals("[LOG] Deposit successful! Your new balance is: 100.00 BGN.", res);
    }

    @Test
    void testSuccessfullyChangedWalletStatusFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_CHANGED_WALLET_STATUS, "ACTIVE");
        assertEquals("[LOG] Wallet status successfully changed to ACTIVE.", res);
    }

    @Test
    void testSuccessfulFundsTransferFormat() {
        String res = String.format(LogMessages.SUCCESSFUL_FUNDS_TRANSFER, "nika", 20.0, "ali", 140.75);
        assertEquals("[LOG] nika transferred 20.00 to ali.\n[LOG] Left balance in this wallet 140.75", res);

    }
}


