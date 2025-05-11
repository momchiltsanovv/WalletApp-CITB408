package org.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemErrorsTest {
    @Test
    void testInvalidUsername() {
        assertEquals(
                "Error: Invalid username, make sure the length is bigger than 4 and contains at least 1 digit.",
                SystemErrors.INVALID_USERNAME
                    );
    }

    @Test
    void testInvalidPassword() {
        assertEquals(
                "Error: Invalid password, make sure the length is exactly 6 and contains only digits.",
                SystemErrors.INVALID_PASSWORD
                    );
    }

    @Test
    void testUserAlreadyLoggedInFormat() {
        String actual = String.format(SystemErrors.USER_ALREADY_LOGGED_IN, "pesho");
        assertEquals("Error: User pesho is already logged in.", actual);
    }

    @Test
    void testIncorrectLoginCredentials() {
        assertEquals(
                "Error: Incorrect username/password.",
                SystemErrors.INCORRECT_LOGIN_CREDENTIALS
                    );
    }

    @Test
    void testSuchUsernameAlreadyExistFormat() {
        String actual = String.format(SystemErrors.SUCH_USERNAME_ALREADY_EXIST, "maria");
        assertEquals("Error: Username maria is already in use.", actual);
    }

    @Test
    void testNoActiveUserSessionFound() {
        assertEquals(
                "Error: No active user session found. Please log in first.",
                SystemErrors.NO_ACTIVE_USER_SESSION_FOUND
                    );
    }

    @Test
    void testInsufficientFundsInWallet() {
        assertEquals(
                "Error: Insufficient funds in wallet.",
                SystemErrors.INSUFFICIENT_FUNDS_IN_WALLET
                    );
    }

    @Test
    void testNoOperationsAllowedForNonActiveWallet() {
        assertEquals(
                "Error: This wallet is non-active and cannot process transactions. No operations allowed for this wallet.",
                SystemErrors.NO_OPERATIONS_ALLOWED_FOR_NON_ACTIVE_WALLET
                    );
    }

    @Test
    void testWithdrawalLimitReachedForDisposableWallet() {
        assertEquals(
                "Error: Withdrawal limit reached. This disposable wallet is now inactive and cannot process further transactions.",
                SystemErrors.WITHDRAWAL_LIMIT_REACHED_FOR_DISPOSABLE_WALLET
                    );
    }

    @Test
    void testSavingsPeriodNotConcludedYetFormat() {
        String actual = String.format(SystemErrors.SAVINGS_PERIOD_NOT_CONCLUDED_YET, 45);
        assertEquals(
                "Error: Withdrawals are not permitted until the savings period has concluded. 45 seconds left.",
                actual
                    );
    }

    @Test
    void testIncorrectWalletType() {
        assertEquals(
                "Error: Invalid wallet type. Please choose from [Standard, Savings, Disposable].",
                SystemErrors.INCORRECT_WALLET_TYPE
                    );
    }

    @Test
    void testStandardWalletCountLimitReached() {
        assertEquals(
                "Error: Maximum limit of standard wallets reached. You are allowed to have only 1 standard wallets.",
                SystemErrors.STANDARD_WALLET_COUNT_LIMIT_REACHED
                    );
    }

    @Test
    void testWalletNotAssociatedWithThisUserFormat() {
        String actual = String.format(SystemErrors.WALLET_NOT_ASSOCIATED_WITH_THIS_USER, "valio");
        assertEquals(
                "Error: User valio is not associated with such wallet.",
                actual
                    );
    }

    @Test
    void testIncorrectWalletStatus() {
        assertEquals(
                "Error: Invalid wallet status. Please choose from [ACTIVE, INACTIVE].",
                SystemErrors.INCORRECT_WALLET_STATUS
                    );
    }

    @Test
    void testNoWalletFoundForReceiverFormat() {
        String actual = String.format(SystemErrors.NO_WALLET_FOUND_FOR_RECEIVER, "maria");
        assertEquals(
                "Error: Receiver maria has no Standard wallet. Can't initiate transfer.",
                actual
                    );
    }

    @Test
    void testTransferCriteriaNotMet() {
        assertEquals(
                "Error: You won't be able to initiate this transfer due to criteria not met.",
                SystemErrors.TRANSFER_CRITERIA_NOT_MET
                    );
    }
}