package com.pra.desafio.service;


import com.pra.desafio.DesafioApplication;
import com.pra.desafio.dto.StringDateDTO;
import com.pra.desafio.dto.TransactionsDTO;
import com.pra.desafio.exception.AccountNotFoundException;
import com.pra.desafio.exception.CategoryNotFoundException;
import com.pra.desafio.exception.ValidateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {DesafioApplication.class})

public class TransactionsServiceTests {

    @Autowired
    private TransactionsService transactionsService;


    @Test
    public void  insertTransactionWithNotExistAccountTest(){
        TransactionsDTO  trans = new  TransactionsDTO();
        trans.setType("E");
        trans.setAccountID(99999);
        assertThatThrownBy(() ->  transactionsService.insertTransaction(trans)).isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    public void  insertTransactionWithNotExistCategoryTypeTest(){
        TransactionsDTO  trans = new  TransactionsDTO();
        trans.setType("X");
        trans.setAccountID(1);
        assertThatThrownBy(() ->  transactionsService.insertTransaction(trans)).isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    public void  listTransactiosWithInvalidDate(){
        assertThatThrownBy(() ->  new  StringDateDTO("1-12-2020")).isInstanceOf(ValidateException.class);
    }

    @Test
    public void  listTransactiosWithInvalidAccount(){
        var startDate = new StringDateDTO("1/12/2020");
        var endDate =   new StringDateDTO("1/12/2021");
         int accountID = 999;
        assertThatThrownBy(() ->  transactionsService.listAllAccountTransactions(accountID,startDate,endDate)).isInstanceOf(AccountNotFoundException.class);
    }

}
