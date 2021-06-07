package com.pra.desafio.dao.categories.jdbc;


import com.pra.desafio.dto.CategoriesDTO;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;



public class CategoriesRowMapper implements RowMapper<CategoriesDTO> {

    @Override
    public CategoriesDTO mapRow( ResultSet rs, int arg1) throws SQLException {
         var dto = new CategoriesDTO();
        dto.setCategoryId(rs.getInt("category_id"));
        dto.setName(rs.getString("name"));
        dto.setType(rs.getString("type"));
        return dto;
    }

}
