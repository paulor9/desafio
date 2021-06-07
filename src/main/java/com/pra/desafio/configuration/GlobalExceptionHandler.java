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


    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound( AccountAlreadyExistException ex) {
        var response = new ExceptionResponse();
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound( UserNotFoundException ex) {
        var response = new ExceptionResponse();
        response.setErrorCode(NOT_FOUND);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound( TransactionNotFoundException ex) {
        var response = new ExceptionResponse();
        response.setErrorCode(NOT_FOUND);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(CategoryNotFoundException ex) {
        var response = new ExceptionResponse();
        response.setErrorCode(NOT_FOUND);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
       return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(AccountNotFoundException ex) {
        var response = new ExceptionResponse();
        response.setErrorCode(NOT_FOUND);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
        var response = new ExceptionResponse();
        response.setErrorCode(NOT_FOUND);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
       return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> resourceAlreadyExists(ResourceAlreadyExists ex) {
        var response=new ExceptionResponse();
        response.setErrorCode("CONFLICT");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
      return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customException(CustomException ex) {
         var response=new ExceptionResponse();
        response.setErrorCode("BAD_REQUEST");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ExceptionResponse> validateException(ValidateException ex) {
        var response=new ExceptionResponse();
        response.setErrorCode("BAD_REQUEST");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException ex) {
        var  response=new ExceptionResponse();
        response.setErrorCode("UNAUTHORIZED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler( NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> noHandlerFoundException( NoHandlerFoundException ex) {
        var response=new ExceptionResponse();
        response.setErrorCode("API METHOD NOT FOUND");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
    }



}