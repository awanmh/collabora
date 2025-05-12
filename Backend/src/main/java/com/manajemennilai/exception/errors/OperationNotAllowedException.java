// OperationNotAllowedException.java

package com.manajemennilai.exception.errors;

/**
 * Exception untuk operasi yang tidak diizinkan.
 */
public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException(String message) {
        super(message);
    }
}