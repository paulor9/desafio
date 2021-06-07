package com.pra.desafio.dao.users.jdbc;

import com.pra.desafio.dao.users.UsersDAO;
import com.pra.desafio.dto.UsersDTO;
import com.pra.desafio.exception.UserNotFoundException;
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
public class UsersDAOImp implements UsersDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsersDAOImp.class);
    private static final String QUERY_ALL_USERS= "SELECT * FROM `desafio`.`users` ";
    private static final String QUERY_USER_BY_ID= "SELECT * FROM desafio.users WHERE user_id = ? ";
    private static final String QUERY_USER_BY_EMAIL= "SELECT * FROM desafio.users WHERE email = ? ";
    private static final String QUERY_INSERT_USER = "INSERT INTO `desafio`.`users`( `name`, `email`) values (?, ?)";
    private static final String QUERY_UPDATE_USER = "UPDATE `desafio`.`users` SET `name` = ?, `email` =  ? WHERE `user_id` = ?";
    private static final String QUERY_DELETE_USER = "DELETE FROM `desafio`.`users`  WHERE `user_id` = ?";

    private final JdbcTemplate jdbcTemplate;

    public UsersDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UsersDTO>listAllUsers(){
      return jdbcTemplate.query(QUERY_ALL_USERS ,new UsersRowMapper());
   }

    @Transactional
    public UsersDTO insertUser(UsersDTO usr) throws UserNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString (1, usr.getName());
            ps.setString (2, usr.getEmail());
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var r = jdbcTemplate.update(psc,keyHolder);
        if ( ( r > 0  ) && (! keyHolder.getKeyList().isEmpty())) {
            Number i = keyHolder.getKey();
            if ( i != null )
                usr.setUserId(i.intValue());
        }
        return usr;
    }

    @Transactional
    public UsersDTO updateUser(UsersDTO usr) throws UserNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_UPDATE_USER , Statement.RETURN_GENERATED_KEYS);
            ps.setString (1, usr.getName());
            ps.setString (2, usr.getEmail());
            ps.setInt (3, usr.getUserId());
            return ps;
        };
         jdbcTemplate.update(psc);
        return usr;
    }

    @Transactional
    public void  deleteUser(int id ) throws UserNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_DELETE_USER);
            ps.setInt (1, id);
            return ps;
        };

        jdbcTemplate.update(psc);
    }

    public UsersDTO getUserByID(int id) throws UserNotFoundException {
        UsersDTO usr ;
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_USER_BY_ID);
            ps.setInt(1, id);
            return ps;
        };
        logger.debug("query {} ", (QUERY_USER_BY_ID ));
        logger.debug("User ID {}", id);
        List<UsersDTO> usrList = jdbcTemplate.query(psc, new UsersRowMapper());
        if (  (!usrList.isEmpty()) ){
            usr = usrList.get(0);
        }else{
            logger.debug("User ID {} not found",  id );
            throw  new UserNotFoundException (String.format("User ID %s not found",  id ));
        }
        return usr;
    }

    public UsersDTO getUserByEMail(String email) {
        UsersDTO usr;
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps;
            ps = conn.prepareStatement(QUERY_USER_BY_EMAIL);
            ps.setString(1, email);
            return ps;
        };
        logger.debug("query {} ", (QUERY_USER_BY_EMAIL ));
        logger.debug("User email {}", email);
        List<UsersDTO> usrList = jdbcTemplate.query(psc, new UsersRowMapper());
        if (  (!usrList.isEmpty()) ){
            usr = usrList.get(0);
        }else{
            logger.debug("User with email  {} not found",  email);
            throw  new UserNotFoundException (String.format("User with email  %s not found",  email));
        }
        return usr;
    }



}
