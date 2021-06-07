package com.pra.desafio.dao.transactions;


import com.pra.desafio.dto.StringDateDTO;
import com.pra.desafio.dto.TransactionsDTO;
import com.pra.desafio.exception.AccountNotFoundException;
import com.pra.desafio.exception.CategoryNotFoundException;
import com.pra.desafio.exception.TransactionNotFoundException;

import java.util.List;

public interface TransactionsDAO {



     public List<TransactionsDTO> listAllAccountTransactions(int accountID);

     public List<TransactionsDTO> listAllAccountTransactions( int accountID, StringDateDTO beginDate, StringDateDTO endDate);

     public TransactionsDTO  insertTransaction( TransactionsDTO tran) throws AccountNotFoundException, CategoryNotFoundException ;

     public void deleteTransaction(int id ) throws TransactionNotFoundException;
}
