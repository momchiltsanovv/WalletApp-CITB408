package org.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletOperationExceptionTest {
    @Test
    void test_ExceptionHasMessage() {
        WalletOperationException ex = new WalletOperationException("Something went wrong!");
        assertEquals("Something went wrong!", ex.getMessage());
    }

    @Test
    void test_ExceptionIsRuntimeException() {
        WalletOperationException ex = new WalletOperationException("msg");
        assertInstanceOf(RuntimeException.class, ex);
    }

    @Test
    void test_Throw() {
        Exception thrown = assertThrows(
                WalletOperationException.class,
                () -> { throw new WalletOperationException("Custom error!"); }
                                       );
        assertEquals("Custom error!", thrown.getMessage());
    }
}