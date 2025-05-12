// DatabaseException.java

package com.manajemennilai.exception.errors;

/**
 * Exception untuk kesalahan database.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}