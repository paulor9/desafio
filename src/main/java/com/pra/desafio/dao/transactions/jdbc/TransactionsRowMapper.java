package com.pra.desafio.dao.transactions.jdbc;

import com.pra.desafio.dto.TransactionsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TransactionsRowMapper implements RowMapper<TransactionsDTO> {

    @Override
    public TransactionsDTO mapRow( ResultSet rs, int arg1) throws SQLException {
        var dto = new TransactionsDTO();
        dto.setTransactionId(rs.getInt("transaction_id"));
        dto.setAccountID(rs.getInt("account_id"));
        dto.setType(rs.getString("type"));
        dto.setCreateAt(rs.getTimestamp("create_at"));
        dto.setValue(rs.getBigDecimal("value"));
        return dto;
    }

}
