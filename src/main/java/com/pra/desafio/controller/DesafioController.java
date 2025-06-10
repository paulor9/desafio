package com.pra.desafio.controller;

import com.pra.desafio.dto.*;
import com.pra.desafio.service.AccountService;
import com.pra.desafio.service.TransactionsService;
import com.pra.desafio.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
     * @param newUser User data ( nome , email ) "
     * @return UsersDTO
     */


    @PostMapping("/desafio/api/v1/user")
    @Operation(summary = "create new user  ")
    public UsersDTO newUser(@Parameter(description = "User data ( nome , email ) ")  @RequestBody UsersBasicDTO newUser) {
        logger.debug("newUser {} " , newUser.getName());
        var usr = new UsersDTO(newUser);
        return userService.insertUser(usr);

    }

    /**
     * API para retornar  o saldo de uma conta
     * @param accountID  account id
     * @return Json with list of Transactions
     */
    @GetMapping("/desafio/api/v1/accountBalance/{accountID}")
    @Operation(summary = "Return Account Balance ")
    public BigDecimal getAccountBalance(@Parameter(description = "Account ID")  @PathVariable int accountID) {
        return  accountService.getAccountBalance(accountID);
    }


    /**
     * API para criar um conta (account ) de um  usuario
     * @param newAccount AccountsBasicDTO
     * @return  AccountsDTO
     */

    @PostMapping("/desafio/api/v1/account")
    @Operation(summary = "create new account ")
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
    @Operation(summary = "create new transaction expense  ")
    public TransactionsDTO newTransactionExpense( @RequestBody TransactionsBasicDTO newPar) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "S");
        return transactionsService.insertTransaction(acc);
    }


    /**
     * API para incluir um lancamento de credito (income ) em conta
     * @return  TransactionsDTO
     */

    @PostMapping("/desafio/api/v1/transaction/income")
    @Operation(summary = "create new transaction income")
    public TransactionsDTO newTransactionIncome( @Parameter(description = "TransactionsBasicDTO") @RequestBody TransactionsBasicDTO newPar) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "E");
        return transactionsService.insertTransaction(acc);
    }



    /**
     * API para retornar todos os lancamento de uma conta (account)
     * @param accountID : Account ID (int)
     * @return Json with list of Transactions
     */

    @GetMapping("/desafio/api/v1/transactions/{accountID}")
    @Operation(summary = "Desafio Extrato", description = "Retorna todos os  lançamentos de uma conta  ")
    public List<TransactionsDTO> all( @Parameter(description = "Account ID")  @PathVariable Integer accountID) {
        return transactionsService.listAllAccountTransactions(accountID);
    }

    /**
     * API para retornar os lancamento de uma conta (account)
     * @param accountID : Account ID (int)
     * @param beginDT  : Data inicial (dd/mm/aaaa)
     * @param endDT    : Data Final (dd/mm/aaaa)
     * @return Json with list of Transactions
     */

    @GetMapping(value = "/desafio/api/v1/transactionsByRange", params = {"accountID", "beginDT", "endDT"} )
    @Operation(summary = "Desafio Extrato", description = "Retorna os lançamentos de uma conta por intervalo de datas ")
    public List<TransactionsDTO> transactionsByRange( @Parameter(description = "Account ID") @RequestParam Integer accountID,
                                                     @Parameter(description = "Begin Date (dd/mm/yyyy)") @RequestParam String beginDT,
                                                     @Parameter(description = "End Date (dd/mm/yyyy)") @RequestParam String endDT) {
        var dtStart = new StringDateDTO(beginDT);
        var  dtEnd = new StringDateDTO(endDT);
        return transactionsService.listAllAccountTransactions(accountID,dtStart,dtEnd);
    }


    /**
     * API para retornar sugestões de melhorias para o projeto
     * @return Map com categorias de melhorias e suas sugestões
     */
    @GetMapping("/desafio/api/v1/improvements")
    @Operation(summary = "Desafio Melhorias", description = "Retorna sugestões de melhorias para o projeto")
    public java.util.Map<String, java.util.List<String>> getImprovements() {
        java.util.Map<String, java.util.List<String>> improvements = new java.util.HashMap<>();

        // Adiciona as categorias e suas sugestões
        improvements.put("Qualidade e Organização do Código", java.util.Arrays.asList(
            "Replace @Component with @Service annotation for service classes",
            "Use constructor injection consistently (already implemented in most places)",
            "Remove empty lines and ensure consistent code formatting",
            "Use interfaces instead of implementations in constructor parameters",
            "Replace string literals for transaction types with enum constants"
        ));

        improvements.put("Performance", java.util.Arrays.asList(
            "Add indexes to frequently queried columns in the database",
            "Consider caching for frequently accessed data",
            "Implement pagination for endpoints that return lists"
        ));

        improvements.put("Segurança", java.util.Arrays.asList(
            "Implement authentication and authorization (e.g., Spring Security)",
            "Add input sanitization to prevent SQL injection and XSS attacks",
            "Implement rate limiting to prevent abuse",
            "Add CORS configuration",
            "Consider using HTTPS for all communications"
        ));

        improvements.put("Documentação", java.util.Arrays.asList(
            "Add comprehensive README.md with setup instructions",
            "Document API endpoints and usage",
            "Add Javadoc comments to all public methods and classes"
        ));

        improvements.put("Testes", java.util.Arrays.asList(
            "Increase test coverage by adding tests for controllers, DAOs, and edge cases",
            "Add integration tests for API endpoints",
            "Consider implementing test containers for database tests"
        ));

        improvements.put("Design de API", java.util.Arrays.asList(
            "Fix typos in endpoint paths",
            "Consider simplifying API paths by removing the verbose prefix",
            "Add input validation using Bean Validation (JSR-380)",
            "Ensure consistent use of parameter annotations across all endpoints",
            "Enhance API documentation with more detailed descriptions and examples"
        ));

        return improvements;
    }
}
