package com.pra.desafio.service;


import com.pra.desafio.DesafioApplication;
import com.pra.desafio.dto.AccountsDTO;
import com.pra.desafio.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(classes = {DesafioApplication.class})
public class AccountServiceTests {

    @Autowired
    private AccountService serviceAccount;



    @Test
    public void getAccountBalanceTest(){
     var saldo =  serviceAccount.getAccountBalance(1);
      assertThatNoException();
    }

    @Test
    public void  insertAccountWithNotExistUserTest(){
        AccountsDTO acc = new AccountsDTO();
        acc.setUserID(9999);
        assertThatThrownBy(() ->  serviceAccount.insertAccount(acc)).isInstanceOf(UserNotFoundException.class);

    }


}
