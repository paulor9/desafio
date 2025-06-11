package com.pra.desafio.configuration;

import com.pra.desafio.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String NOT_FOUND = "NOT_FOUND";
    private static final String CONFLICT = "CONFLICT";
    private static final String BAD_REQUEST = "BAD_REQUEST";
    private static final String UNAUTHORIZED = "UNAUTHORIZED";
    private static final String API_METHOD_NOT_FOUND = "API METHOD NOT FOUND";

    /**
     * Helper method to create and configure an ExceptionResponse
     * 
     * @param errorCode the error code to set
     * @param ex the exception containing the error message
     * @param status the HTTP status to return
     * @return ResponseEntity with configured ExceptionResponse
     */
    private ResponseEntity<ExceptionResponse> createExceptionResponse(String errorCode, Exception ex, HttpStatus status) {
        var response = new ExceptionResponse();
        response.setErrorCode(errorCode);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, status);
    }

    // Not Found exceptions
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return createExceptionResponse(NOT_FOUND, ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        return createExceptionResponse(NOT_FOUND, ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return createExceptionResponse(NOT_FOUND, ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        return createExceptionResponse(NOT_FOUND, ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return createExceptionResponse(NOT_FOUND, ex, HttpStatus.NOT_FOUND);
    }

    // Conflict exceptions
    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleAccountAlreadyExistException(AccountAlreadyExistException ex) {
        return createExceptionResponse(CONFLICT, ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> handleResourceAlreadyExists(ResourceAlreadyExists ex) {
        return createExceptionResponse(CONFLICT, ex, HttpStatus.CONFLICT);
    }

    // Bad Request exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException ex) {
        return createExceptionResponse(BAD_REQUEST, ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ExceptionResponse> handleValidateException(ValidateException ex) {
        return createExceptionResponse(BAD_REQUEST, ex, HttpStatus.BAD_REQUEST);
    }

    // Unauthorized exceptions
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return createExceptionResponse(UNAUTHORIZED, ex, HttpStatus.UNAUTHORIZED);
    }

    // Not Implemented exceptions
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return createExceptionResponse(API_METHOD_NOT_FOUND, ex, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex) {
        return createExceptionResponse("Duplicate entry", ex, HttpStatus.CONFLICT);
    }
}
