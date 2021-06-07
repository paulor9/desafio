package com.pra.desafio.dao.accounts;


import com.pra.desafio.dao.accounts.jdbc.AccountsDAO;
import com.pra.desafio.dao.accounts.jdbc.AccountsRowMapper;
import com.pra.desafio.dto.AccountsDTO;
import com.pra.desafio.exception.AccountNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class AccountDAOImp implements AccountsDAO {
    private static final Logger logger = LoggerFactory.getLogger(AccountDAOImp.class);
    private static final String QUERY_ALL_ACCOUNTS= "SELECT * FROM `desafio`.`accounts`";
    private static final String QUERY_ACCOUNT_BY_ID= "SELECT * FROM `desafio`.`accounts` WHERE account_id = ? ";
    private static final String QUERY_ACCOUNT_BY_USER_ID = "SELECT * FROM `desafio`.`accounts` WHERE user_id = ? ";
    private static final String QUERY_INSERT_ACCOUNT = "INSERT INTO `desafio`.`accounts`( `user_id`, `balance`, `income`, `expense`) values (?, 0.0,0.0,0.0)";
    private static final String QUERY_UPDATE_ACCOUNT = "UPDATE `desafio`.`accounts` SET `user_id` = ?, `balance` =  ?, `income` = ?, `expense` = ?  WHERE `account_id` = ?";
    private static final String QUERY_DELETE_ACCOUNT = "DELETE FROM `desafio`.`accounts`  WHERE `account_id` = ?";

    private final JdbcTemplate jdbcTemplate;

    public AccountDAOImp( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AccountsDTO> listAllAccounts(){
      return jdbcTemplate.query( QUERY_ALL_ACCOUNTS,new AccountsRowMapper());
   }


    public AccountsDTO insertAccount( AccountsDTO acc) throws AccountNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt (1, acc.getUserID());
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var r = jdbcTemplate.update(psc,keyHolder);
        if ( ( r > 0  ) && (! keyHolder.getKeyList().isEmpty())) {
            Number i = keyHolder.getKey();
            if ( i != null )
                acc.setAccountId(i.intValue());
        }
        return acc;
    }

    @Transactional
    public AccountsDTO updateAccount(AccountsDTO acc) throws AccountNotFoundException{
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_UPDATE_ACCOUNT , Statement.RETURN_GENERATED_KEYS);
            ps.setInt (1, acc.getUserID());
            ps.setBigDecimal (2, acc.getBalance());
            ps.setBigDecimal (3, acc.getIncome());
            ps.setBigDecimal (4, acc.getExpense());
            ps.setInt (5, acc.getAccountId());
            return ps;
        };
         jdbcTemplate.update(psc);
        return acc;
    }

    @Transactional
    public void  deleteAccount(int id ) throws AccountNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_DELETE_ACCOUNT);
            ps.setInt (1, id);
            return ps;
        };

        jdbcTemplate.update(psc);
    }

    public AccountsDTO  getAccountByID(int id) throws AccountNotFoundException {
        AccountsDTO acc ;
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_ACCOUNT_BY_ID);
            ps.setInt(1, id);
            return ps;
        };
        List<AccountsDTO> accList = jdbcTemplate.query(psc, new AccountsRowMapper());
        if (  (!accList.isEmpty()) ){
            acc = accList.get(0);
        }else{
            logger.debug("Account ID {} not found",  id );
            throw  new AccountNotFoundException (String.format("Account ID %s not found",  id ));
        }
        return acc;
    }

    @Override
    public boolean existAccountByID( int id ) throws AccountNotFoundException {
         PreparedStatementCreator psc = conn -> {
            PreparedStatement ps;
            ps = conn.prepareStatement(QUERY_ACCOUNT_BY_USER_ID);
            ps.setInt(1, id);
            return ps;
        };
        List<AccountsDTO> accList = jdbcTemplate.query(psc, new AccountsRowMapper());
        return !accList.isEmpty();
      }

    public AccountsDTO getAccountByUserID(int id ) {
        AccountsDTO acc;
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps;
            ps = conn.prepareStatement(QUERY_ACCOUNT_BY_USER_ID);
            ps.setInt(1, id);
            return ps;
        };
        List<AccountsDTO> accList = jdbcTemplate.query(psc, new AccountsRowMapper());
        if (  (!accList.isEmpty()) ){
            acc = accList.get(0);
        }else{
            logger.debug("Account with userID  {} not found",  id);
            throw  new AccountNotFoundException (String.format("Account with userID  %s not found", id));
        }
        return acc;
    }



}
