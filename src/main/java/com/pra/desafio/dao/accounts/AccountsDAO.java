package com.pra.desafio.dao.accounts;

import com.pra.desafio.dto.AccountsDTO;
import com.pra.desafio.exception.AccountNotFoundException;

import java.util.List;

public interface AccountsDAO {

    public List<AccountsDTO> listAllAccounts();

    public AccountsDTO insertAccount(AccountsDTO acc) throws AccountNotFoundException;

    public AccountsDTO updateAccount(AccountsDTO acc) throws AccountNotFoundException;

    public void deleteAccount(int id ) throws AccountNotFoundException;

    public AccountsDTO getAccountByID(int id) throws AccountNotFoundException;

    public boolean existAccountByID(int id) throws AccountNotFoundException;


    public AccountsDTO getAccountByUserID(int id);
}
