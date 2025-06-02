package com.manajemennilai.exception;

import com.manajemennilai.exception.errors.*;
import com.manajemennilai.exception.response.*;
import io.jsonwebtoken.JwtException; // Ditambahkan
import org.slf4j.Logger; // Ditambahkan
import org.slf4j.LoggerFactory; // Ditambahkan
import org.springframework.dao.DataAccessException; // Menggantikan DatabaseException
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException; // Ditambahkan
import org.springframework.web.HttpMediaTypeNotSupportedException; // Ditambahkan
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handler untuk menangani semua exception kustom.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class); // Ditambahkan

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource tidak ditemukan: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailed(AuthenticationFailedException ex) {
        logger.error("Autentikasi gagal: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleOperationNotAllowed(OperationNotAllowedException ex) {
        logger.error("Operasi tidak diizinkan: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        logger.error("Error validasi: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class) // Menggantikan DatabaseException
    public ResponseEntity<ErrorResponse> handleDatabase(DataAccessException ex) {
        logger.error("Error database: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error database: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class) // Ditambahkan
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        logger.error("Tipe media tidak didukung: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Tipe media tidak didukung: " + ex.getContentType());
        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) // Ditambahkan
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.error("Isi permintaan hilang atau tidak dapat dibaca: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Isi permintaan hilang atau tidak dapat dibaca: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class) // Ditambahkan
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        logger.error("Error JWT: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Pemrosesan JWT gagal: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        logger.error("Terjadi kesalahan tak terduga: {}", ex.getMessage(), ex); // Ditambahkan logging
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Terjadi kesalahan tak terduga: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}