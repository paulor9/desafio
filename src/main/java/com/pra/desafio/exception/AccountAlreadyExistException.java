package com.pra.desafio.exception;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException( String msg) {
        super(msg);
    }
}
