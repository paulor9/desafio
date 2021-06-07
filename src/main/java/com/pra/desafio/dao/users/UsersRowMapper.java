package com.pra.desafio.dao.users;

import com.pra.desafio.dto.UsersDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UsersRowMapper implements RowMapper<UsersDTO> {

    @Override
    public UsersDTO mapRow( ResultSet rs, int rownumber) throws SQLException {
        var  dto = new UsersDTO();
        dto.setUserId(rs.getInt("user_id"));
        dto.setEmail(rs.getString("email"));
        dto.setName(rs.getString("name"));
        return dto;
    }

}
