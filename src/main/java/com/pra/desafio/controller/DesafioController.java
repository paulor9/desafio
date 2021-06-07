package com.pra.desafio.controller;

import com.pra.desafio.dto.*;
import com.pra.desafio.service.AccountService;
import com.pra.desafio.service.TransactionsService;
import com.pra.desafio.service.UsersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class DesafioController {

    private final TransactionsService transactionsService;
    private final UsersService userService;
    private final AccountService accountService;

    Logger logger = LoggerFactory.getLogger(DesafioController.class);

    public DesafioController( TransactionsService transactionsService, UsersService userService, AccountService accountService) {
        this.transactionsService = transactionsService;
        this.userService  =  userService;
        this.accountService = accountService;

    }

    /**
     * API para criar um "user"
     * @param "User data ( nome , email ) "
     * @return Json with list of Transactions
     */


    @PostMapping("/desafio/api/v1/user")
    @ApiOperation(value = "create new user  ")
    public UsersDTO newUser(@ApiParam(value = "User data ( nome , email ) ")  @RequestBody UsersBasicDTO newUser) {
        logger.debug("newUser {} " , newUser.getName());
        var usr = new UsersDTO(newUser);
        return userService.insertUser(usr);

    }

    /**
     * API para retornar  o saldo de uma conta
     * @param "Account ID"
     * @return Json with list of Transactions
     */
    @GetMapping("/desafio/api/v1/accountBalace/{accountID}")
    @ApiOperation(value = "Return Account Balance ")
    public BigDecimal getAccountBalance(@ApiParam(value = "Account ID")  @PathVariable int accountID) {
        return  accountService.getAccountBalance(accountID);
    }


    /**
     * API para criar um conta (account ) de um  usuario
     * @return  Transacao criada ( json)
     */

    @PostMapping("/desafio/api/v1/account")
    @ApiOperation(value = "create new account ")
    public AccountsDTO newAccount( @RequestBody AccountsBasicDTO newAccount) {
        logger.debug("newAccount{} " , newAccount.getUserID());
        var acc = new AccountsDTO(newAccount);
        return accountService.insertAccount(acc);

    }


    /**
     * API para incluir um lancamento de debito  (expense) em conta
     * @return  Transacao criada ( json)
     */
    @PostMapping("/desafio/api/v1/transaction/expense")
    @ApiOperation(value = "create new transaction expense  ")
    public TransactionsDTO newTransactionExpense( @RequestBody TransactionsBasicDTO newPar) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "S");
        return transactionsService.insertTransaction(acc);
    }


    /**
     * API para incluir um lancamento de credito (income ) em conta
     * @return  Transacao criada ( json)
     */

    @PostMapping("/desafio/api/v1/transaction/income")
    @ApiOperation(value = "create new transaction income")
    public TransactionsDTO newTransactionIncome( @RequestBody TransactionsBasicDTO newPar) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "E");
        return transactionsService.insertTransaction(acc);
    }



    /**
     * API para retornar todos os lancamento de uma conta (account)
     * @param accountID : Account ID (int)
     * @return Json with list of Transactions
     */

    @GetMapping("/desafio/api/v1/transanctions/{accountID}")
    @ApiOperation(value = "Desafio Extrato", notes = "Retorna todos os  lançamentos de uma conta  ")
    public List<TransactionsDTO> all( @ApiParam(value = "Account ID")  @PathVariable Integer accountID) {
        return transactionsService.listAllAccountTransactions(accountID);
    }

    /**
     * API para retornar os lancamento de uma conta (account)
     * @param accountID : Account ID (int)
     * @param beginDT  : Data inicial (dd/mm/aaaa)
     * @param endDT    : Data Final (dd/mm/aaaa)
     * @return Json with list of Transactions
     */

    @GetMapping(value = "/desafio/api/v1/transactiosByRange", params = {"accountID", "beginDT", "endDT"} )
    @ApiOperation(value = "Desafio Extrato", notes = "Retorna os lançamentos de uma conta por intervalo de datas ")
    public List<TransactionsDTO> transactiosByRange( @ApiParam(value = "Account ID") @RequestParam Integer accountID,
                                                     @ApiParam(value = "Begin Date (dd/mm/yyyy)") @RequestParam String beginDT,
                                                     @ApiParam(value = "End Date (dd/mm/yyyy)") @RequestParam String endDT) {
        var dtStart = new StringDateDTO(beginDT);
        var  dtEnd = new StringDateDTO(endDT);
        return transactionsService.listAllAccountTransactions(accountID,dtStart,dtEnd);
    }



}
