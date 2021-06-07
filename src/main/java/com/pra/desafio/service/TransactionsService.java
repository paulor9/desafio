package com.pra.desafio.service;

import com.pra.desafio.dao.accounts.AccountsDAO;
import com.pra.desafio.dao.categories.CategoriesDAO;
import com.pra.desafio.dao.transactions.TransactionsDAO;
import com.pra.desafio.dao.transactions.TransactionsDAOImp;
import com.pra.desafio.dto.AccountsDTO;
import com.pra.desafio.dto.StringDateDTO;
import com.pra.desafio.dto.TransactionsDTO;
import com.pra.desafio.exception.AccountNotFoundException;
import com.pra.desafio.exception.CategoryNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TransactionsService {
    private final TransactionsDAO transactionsDAO;
    private final AccountsDAO accountesDAO;
    private final CategoriesDAO categoriesDAO ;





    public TransactionsService( TransactionsDAOImp transactionsDAO,  AccountsDAO accountesDAO, CategoriesDAO categoriesDAO) {
        this.transactionsDAO = transactionsDAO;
        this.accountesDAO = accountesDAO;
        this.categoriesDAO = categoriesDAO;
    }


    public List<TransactionsDTO> listAllAccountTransactions(int accountID) {
        return transactionsDAO.listAllAccountTransactions(accountID);
    }

    public List<TransactionsDTO> listAllAccountTransactions( int accountID, StringDateDTO beginDate, StringDateDTO endDate) throws AccountNotFoundException{
         accountesDAO.getAccountByID(accountID);
        return transactionsDAO.listAllAccountTransactions(accountID,beginDate,endDate);
    }

    @Transactional
    public TransactionsDTO  insertTransaction( TransactionsDTO tran) throws AccountNotFoundException, CategoryNotFoundException {
        AccountsDTO acc = accountesDAO.getAccountByID(tran.getAccountID());
        categoriesDAO.getCategoryByType(tran.getType());
        TransactionsDTO  newTran = transactionsDAO.insertTransaction(tran);
        if ( tran.getType().equals("S")) {
            acc.setExpense(acc.getExpense().add(tran.getValue()));
            acc.setBalance(acc.getBalance().subtract(tran.getValue()));
        }else if ( tran.getType().equals("E")){
            acc.setIncome(acc.getIncome().add(tran.getValue()));
            acc.setBalance(acc.getBalance().add(tran.getValue()));
        }

        accountesDAO.updateAccount(acc);
        return newTran;
    }


}
