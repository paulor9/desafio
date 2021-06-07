package com.pra.desafio.dao.transactions.jdbc;

import com.pra.desafio.dao.transactions.TransactionsDAO;
import com.pra.desafio.dto.StringDateDTO;
import com.pra.desafio.dto.TransactionsDTO;
import com.pra.desafio.exception.AccountNotFoundException;
import com.pra.desafio.exception.CategoryNotFoundException;
import com.pra.desafio.exception.TransactionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class TransactionsDAOImp implements TransactionsDAO {
    private static final Logger logger = LoggerFactory.getLogger(TransactionsDAOImp.class);
    private static final String QUERY_ALL_TRANSACTIONS_BY_ACCOUNT_DATES_RANGE=
    "SELECT * FROM `desafio`.`transactions` WHERE `account_id` = ? and  DATE(`create_at`)  BETWEEN  str_to_date( ? ,'%d/%m/%Y')  AND str_to_date( ? ,'%d/%m/%Y')";
    private static final String QUERY_ALL_TRANSACTIONS_BY_ACCOUNT= "SELECT * FROM `desafio`.`transactions` WHERE `account_id` = ? ";
    private static final String QUERY_INSERT_TRANSACTION = "INSERT INTO `desafio`.`transactions`( `account_id`, `value`, `type`) values (?, ?, ?)";
    private static final String QUERY_DELETE_TRANSACTION= "DELETE FROM `desafio`.`transactions`  WHERE `transaction_id` = ?";




    private final JdbcTemplate jdbcTemplate;

    public TransactionsDAOImp( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TransactionsDTO> listAllAccountTransactions(int accountID){
       PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_ALL_TRANSACTIONS_BY_ACCOUNT);
            ps.setInt(1, accountID);
            return ps;
        };
        logger.debug("query {} ", QUERY_ALL_TRANSACTIONS_BY_ACCOUNT );
        logger.debug(" account ID {}", accountID);
        return  jdbcTemplate.query(psc, new TransactionsRowMapper());

   }

    public List<TransactionsDTO> listAllAccountTransactions( int accountID, StringDateDTO beginDate, StringDateDTO endDate){
      PreparedStatementCreator psc = conn -> {
             PreparedStatement ps = conn.prepareStatement(QUERY_ALL_TRANSACTIONS_BY_ACCOUNT_DATES_RANGE);
             ps.setInt(1, accountID);
             ps.setString(2,beginDate.getDateStr());
             ps.setString(3,endDate.getDateStr());
            return ps;
         };
        logger.debug("query {} ",QUERY_ALL_TRANSACTIONS_BY_ACCOUNT_DATES_RANGE );
        logger.debug("Account ID {}", accountID);
        logger.debug("beginDate {}", beginDate.getDateStr());
        logger.debug("endDate {}", endDate.getDateStr());
         return  jdbcTemplate.query(psc, new TransactionsRowMapper());

    }


    public TransactionsDTO  insertTransaction( TransactionsDTO tran) throws AccountNotFoundException, CategoryNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, tran.getAccountID());
            ps.setBigDecimal(2,tran.getValue());
            ps.setString (3, tran.getType());
            logger.debug("query {} ", (QUERY_INSERT_TRANSACTION ));
            logger.debug("Account ID {}", tran.getAccountID());
            logger.debug("Value {}", tran.getValue());
            logger.debug("type  {}", tran.getType());
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var r = jdbcTemplate.update(psc,keyHolder);
        if ( ( r > 0  ) && (!keyHolder.getKeyList().isEmpty())) {
            Number i = keyHolder.getKey();
            if ( i != null )
                tran.setTransactionId(i.intValue());
        }
        return tran;
    }

    public void deleteTransaction(int id ) throws TransactionNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_DELETE_TRANSACTION);
            ps.setInt (1, id);
            return ps;
        };
        logger.debug("query {} ", (QUERY_DELETE_TRANSACTION ));
        logger.debug("id  {}", id);
       jdbcTemplate.update(psc);
    }





}
