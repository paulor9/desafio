package com.pra.desafio.controller;

import com.pra.desafio.dto.StringDateDTO;
import com.pra.desafio.dto.TransactionsBasicDTO;
import com.pra.desafio.dto.TransactionsDTO;
import com.pra.desafio.service.TransactionsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionsController {
    private final TransactionsService transactionsService;

    Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    public TransactionsController( TransactionsService transactionsService ) {
        this.transactionsService = transactionsService;
    }



    @PostMapping("/api/v1/transaction/expense")
    @ApiOperation(value = "create new transaction expense  ")
    public TransactionsDTO newTransactionExpense( @RequestBody TransactionsBasicDTO newPar) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "S");
        return transactionsService.insertTransaction(acc);
    }


    @PostMapping("/api/v1/transaction/income")
    @ApiOperation(value = "create new transaction income")
    public TransactionsDTO newTransactionIncome( @RequestBody TransactionsBasicDTO newPar) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "E");
        return transactionsService.insertTransaction(acc);
  }


    @GetMapping("/api/v1/transanctions/{accountID}")
    @ApiOperation(value = "Desafio Extrato", notes = "Retorna todos os  lançamento de uma conta  ")
    public List<TransactionsDTO> all(@ApiParam(value = "Account ID")  @PathVariable Integer accountID) {
        return transactionsService.listAllAccountTransactions(accountID);
    }

    /**
     * API para retornar os lancamento de uma conta (account)
     * @param accountID : Account ID (int)
     * @param beginDT  : Data inicial (dd/mm/aaaa)
     * @param endDT    : Data Final (dd/mm/aaaa)
     * @return Json with list of Transactions
     */

    @GetMapping(value = "/api/v1/transactiosByRange", params = {"accountID", "beginDT", "endDT"} )
    @ApiOperation(value = "Desafio Extrato", notes = "Retorna os lançamento de uma conta por intervalod de datas ")
    public List<TransactionsDTO> transactiosByRange( @ApiParam(value = "Account ID") @RequestParam Integer accountID,
                                                     @ApiParam(value = "Begin Date (dd/mm/yyyy)") @RequestParam String beginDT,
                                                     @ApiParam(value = "End Date (dd/mm/yyyy)") @RequestParam String endDT) {
        var dtStart = new StringDateDTO(beginDT);
        var  dtEnd = new StringDateDTO(endDT);
        return transactionsService.listAllAccountTransactions(accountID,dtStart,dtEnd);
    }



}
