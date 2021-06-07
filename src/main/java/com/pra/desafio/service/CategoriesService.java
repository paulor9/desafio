package com.pra.desafio.service;


import com.pra.desafio.dao.categories.CategoriesDAO;
import com.pra.desafio.dao.categories.jdbc.CategoriesDAOImp;
import com.pra.desafio.dto.CategoriesDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriesService {
    private final CategoriesDAO categoriesDAO;



    public CategoriesService( CategoriesDAOImp categoriesDAO ) {
        this.categoriesDAO = categoriesDAO;
    }

    public List<CategoriesDTO> listAllCategories() {
        return categoriesDAO.listAllCategories();
    }



}
