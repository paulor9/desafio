package com.pra.desafio.dto;

import java.io.Serializable;
import java.util.Objects;

public class UsersBasicDTO implements Serializable {

    protected String name;
    protected String email;


    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (UsersBasicDTO) o;
        return  Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, email);
    }

}
