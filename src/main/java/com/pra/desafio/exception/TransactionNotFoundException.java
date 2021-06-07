package com.pra.desafio.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException( String msg) {
        super(msg);
    }
}
