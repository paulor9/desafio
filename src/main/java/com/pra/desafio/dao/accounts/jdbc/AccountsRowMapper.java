package com.pra.desafio.dao.accounts.jdbc;

import com.pra.desafio.dto.AccountsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountsRowMapper implements RowMapper<AccountsDTO> {

    @Override
    public AccountsDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        var dto = new AccountsDTO();
        dto.setAccountId(rs.getInt("account_id"));
        dto.setBalance(rs.getBigDecimal("balance"));
        dto.setExpense(rs.getBigDecimal("expense"));
        dto.setIncome(rs.getBigDecimal("income"));
        dto.setUserID(rs.getInt("user_id"));
        return dto;
    }

}
