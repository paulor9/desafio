package com.pra.desafio.service;


import com.pra.desafio.dao.accounts.jdbc.AccountsDAO;
import com.pra.desafio.dao.users.UsersDAO;
import com.pra.desafio.dto.AccountsDTO;
import com.pra.desafio.exception.AccountNotFoundException;
import com.pra.desafio.exception.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountService {
    private final AccountsDAO accountsDAO;
    private final UsersDAO usersDAO;

    public AccountService( AccountsDAO accountsDAO, UsersDAO usersDAO ) {
        this.accountsDAO = accountsDAO;
        this.usersDAO = usersDAO;
    }

    public List<AccountsDTO> listAllAccounts(){
        return accountsDAO.listAllAccounts();
    }

    @Transactional
    public AccountsDTO insertAccount( AccountsDTO pAcc) throws  UserNotFoundException {
         usersDAO.getUserByID(pAcc.getUserID());
         return accountsDAO.insertAccount(pAcc);
    }

    public BigDecimal getAccountBalance ( int accID) throws AccountNotFoundException {
       AccountsDTO acc = accountsDAO.getAccountByID(accID);
       return acc.getBalance();
  }

}
