package org.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserOperationExceptionTest {

    @Test
    void test_ExceptionHasMessage() {
        UserOperationException ex = new UserOperationException("Something went wrong!");
        assertEquals("Something went wrong!", ex.getMessage());
    }

    @Test
    void test_ExceptionIsRuntimeException() {
        UserOperationException ex = new UserOperationException("msg");
        assertInstanceOf(RuntimeException.class, ex);
    }

    @Test
    void test_Throw() {
        Exception thrown = assertThrows(
                UserOperationException.class,
                () -> {
                    throw new UserOperationException("Custom error!");
                }
                                       );
        assertEquals("Custom error!", thrown.getMessage());
    }
}