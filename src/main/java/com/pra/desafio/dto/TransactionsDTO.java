package com.pra.desafio.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class TransactionsDTO extends TransactionsBasicDTO{
    protected int transactionId;
    protected Timestamp createAt;
    protected String type;

    public TransactionsDTO( ) {
        super();
    }

    public TransactionsDTO( int accountID, BigDecimal value, String type ) {
        this.accountID = accountID;
        this.value = value;
        this.type = type;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId( int transactionId ) {
        this.transactionId = transactionId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt( Timestamp createAt ) {
        this.createAt = createAt;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }
}
