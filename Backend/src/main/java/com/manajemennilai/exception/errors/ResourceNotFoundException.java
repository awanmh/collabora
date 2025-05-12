// ResourceNotFoundException.java

package com.manajemennilai.exception.errors;

/**
 * Exception untuk resource yang tidak ditemukan.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}