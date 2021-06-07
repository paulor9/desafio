package com.pra.desafio.dao.categories;

import com.pra.desafio.dto.CategoriesDTO;
import com.pra.desafio.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoriesDAO {

    public List<CategoriesDTO> listAllCategories();

    public CategoriesDTO insertCategory( CategoriesDTO cat) throws CategoryNotFoundException;

    public CategoriesDTO updateCategory(CategoriesDTO cat) throws CategoryNotFoundException;

    public void  deleteCategory(int id ) throws CategoryNotFoundException;

    public CategoriesDTO  getCategoryByID(int id) throws CategoryNotFoundException;

    public CategoriesDTO getCategoryByType(String type)throws CategoryNotFoundException;

}
