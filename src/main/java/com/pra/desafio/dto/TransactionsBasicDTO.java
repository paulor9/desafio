package com.pra.desafio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionsBasicDTO  implements Serializable {
    protected int accountID;
    protected BigDecimal value;

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID( int accountID ) {
        this.accountID = accountID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue( BigDecimal value ) {
        this.value = value;
    }
}
