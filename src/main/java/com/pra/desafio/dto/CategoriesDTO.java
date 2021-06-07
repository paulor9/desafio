package com.pra.desafio.dto;

import java.io.Serializable;
import java.util.Objects;


public class CategoriesDTO implements Serializable {
    private int categoryId;
    private String name;
    private String type;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId( int categoryId ) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }


    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriesDTO that = (CategoriesDTO) o;
        return categoryId == that.categoryId && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, type);
    }


}
