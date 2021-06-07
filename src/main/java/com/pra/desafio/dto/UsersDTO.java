package com.pra.desafio.dto;

import java.util.Objects;



public class UsersDTO extends UsersBasicDTO {
    private int userId;



    public UsersDTO(){

    }


    public UsersDTO(int id, String email, String name ){
       this.userId = id;
       this.email = email;
       this.name = name;
    }

    public UsersDTO(UsersBasicDTO usr){
       this.userId = 0;
       this.email = usr.getEmail();
       this.name = usr.name;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId( int userId ) {
        this.userId = userId;
    }



    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersDTO that = (UsersDTO) o;
        return userId == that.userId && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, this.email);
    }
}
