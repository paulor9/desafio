package com.pra.desafio.controller;


import com.pra.desafio.dto.AccountsBasicDTO;
import com.pra.desafio.dto.AccountsDTO;
import com.pra.desafio.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController( AccountService accountService) {
        this.accountService = accountService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/v1/accounts")
    public List<AccountsDTO> all() {
        return  accountService.listAllAccounts();
    }

    @GetMapping("/api/v1/accountBalace/{accountID}")
    @ApiOperation(value = "Return Account Balance ")
    public BigDecimal getAccountBalance( @PathVariable int accountID) {
        return  accountService.getAccountBalance(accountID);
    }


    @PostMapping("/api/v1/account")
    @ApiOperation(value = "create new account ")
    public AccountsDTO newAccount( @RequestBody AccountsBasicDTO newPar) {
        logger.debug("newAccount{} " , newPar.getUserID());
        var acc = new AccountsDTO(newPar);
        return accountService.insertAccount(acc);

    }

}
