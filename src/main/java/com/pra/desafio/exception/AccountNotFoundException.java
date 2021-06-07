package com.pra.desafio.exception;

public class AccountNotFoundException extends RuntimeException {

    public  AccountNotFoundException( String msg) {
        super(msg);
    }
}
