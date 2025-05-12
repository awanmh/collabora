// ValidationException.java

package com.manajemennilai.exception.errors;

/**
 * Exception untuk validasi input yang gagal.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}