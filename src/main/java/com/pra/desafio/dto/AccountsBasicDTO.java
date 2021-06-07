package com.pra.desafio.dto;

import java.io.Serializable;

public class AccountsBasicDTO implements Serializable {
    protected int userID;


    public AccountsBasicDTO( ) {
        this.userID = 0;
    }

    public AccountsBasicDTO( int userID ) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID( int userID ) {
        this.userID = userID;
    }
}
