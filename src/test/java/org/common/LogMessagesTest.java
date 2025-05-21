package org.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMessagesTest {

    @Test
    void test_SuccessfullyRegisteredFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_REGISTERED, "pesho");
        assertEquals("[LOG] New user \"pesho\" registered successfully.", res);
    }

    @Test
    void test_SuccessfullyLoggedInFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_LOGGED_IN, "maria");
        assertEquals("[LOG] User maria successfully logged in.", res);
    }

    @Test
    void test_SuccessfullyLoggedOutFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_LOGGED_OUT, "gosho");
        assertEquals("[LOG] User gosho successfully logged out.", res);
    }

    @Test
    void test_ZeroWallets() {
        assertEquals("[LOG] No wallets found. You may create one.", LogMessages.ZERO_WALLETS);
    }

    @Test
    void test_SuccessfullyDepositedAmountFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_DEPOSITED_AMOUNT, "100.00", "BGN");
        assertEquals("[LOG] Deposit successful! Your new balance is: 100.00 BGN.", res);
    }

    @Test
    void test_SuccessfullyChangedWalletStatusFormat() {
        String res = String.format(LogMessages.SUCCESSFULLY_CHANGED_WALLET_STATUS, "ACTIVE");
        assertEquals("[LOG] Wallet status successfully changed to ACTIVE.", res);
    }

    @Test
    void test_SuccessfulFundsTransferFormat() {
        String res = String.format(LogMessages.SUCCESSFUL_FUNDS_TRANSFER, "nika", 20.0, "ali", 140.75);
        assertEquals("[LOG] nika transferred 20.00 to ali.\n[LOG] Left balance in this wallet 140.75", res);

    }
}


