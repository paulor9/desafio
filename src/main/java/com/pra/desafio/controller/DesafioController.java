package com.pra.desafio.controller;

import com.pra.desafio.dto.*;
import com.pra.desafio.exception.ExceptionResponse;
import com.pra.desafio.service.AccountService;
import com.pra.desafio.service.TransactionsService;
import com.pra.desafio.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Tag(name = "Financial Management", description = "APIs for managing users, accounts, and financial transactions")
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
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user with the provided name and email. Returns the created user with a generated user ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "User created successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = UsersDTO.class),
                examples = @ExampleObject(
                    value = "{\"userId\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Invalid user data\", \"errorCode\": \"BAD_REQUEST\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "User already exists",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"User with email john.doe@example.com already exists\", \"errorCode\": \"CONFLICT\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        )
    })
    public UsersDTO newUser(
        @Parameter(
            description = "User data containing name and email",
            required = true,
            schema = @Schema(implementation = UsersBasicDTO.class),
            examples = @ExampleObject(
                value = "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\"}"
            )
        )
        @RequestBody UsersBasicDTO newUser
    ) {
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
    @Operation(
        summary = "Get account balance",
        description = "Returns the current balance of the specified account."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Balance retrieved successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = BigDecimal.class),
                examples = @ExampleObject(value = "1250.75")
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Account not found",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Account with ID 123 not found\", \"errorCode\": \"NOT_FOUND\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        )
    })
    public BigDecimal getAccountBalance(
        @Parameter(
            description = "ID of the account to get balance for",
            required = true,
            example = "1"
        )
        @PathVariable int accountID
    ) {
        return accountService.getAccountBalance(accountID);
    }


    /**
     * API para criar um conta (account ) de um  usuario
     * @param newAccount AccountsBasicDTO
     * @return  AccountsDTO
     */

    @PostMapping("/desafio/api/v1/account")
    @Operation(
        summary = "Create a new account",
        description = "Creates a new account for the specified user. Returns the created account with a generated account ID and initialized balance, income, and expense values."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Account created successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = AccountsDTO.class),
                examples = @ExampleObject(
                    value = "{\"accountId\": 1, \"userID\": 1, \"balance\": 0.00, \"income\": 0.00, \"expense\": 0.00}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"User with ID 1 not found\", \"errorCode\": \"NOT_FOUND\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Account already exists",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Account for user ID 1 already exists\", \"errorCode\": \"CONFLICT\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        )
    })
    public AccountsDTO newAccount(
        @Parameter(
            description = "Account data containing user ID",
            required = true,
            schema = @Schema(implementation = AccountsBasicDTO.class),
            examples = @ExampleObject(
                value = "{\"userID\": 1}"
            )
        )
        @RequestBody AccountsBasicDTO newAccount
    ) {
        logger.debug("newAccount{} " , newAccount.getUserID());
        var acc = new AccountsDTO(newAccount);
        return accountService.insertAccount(acc);
    }


    /**
     * API para incluir um lancamento de debito  (expense) em conta
     * @return  Transacao criada ( json)
     */
    @PostMapping("/desafio/api/v1/transaction/expense")
    @Operation(
        summary = "Create a new expense transaction",
        description = "Records a new expense (debit) transaction for the specified account. Returns the created transaction with a generated transaction ID and timestamp."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Transaction created successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = TransactionsDTO.class),
                examples = @ExampleObject(
                    value = "{\"transactionId\": 1, \"accountID\": 1, \"value\": 50.00, \"createAt\": \"2023-01-01T12:00:00\", \"type\": \"S\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Account not found",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Account with ID 1 not found\", \"errorCode\": \"NOT_FOUND\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid transaction data",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Transaction value must be positive\", \"errorCode\": \"BAD_REQUEST\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        )
    })
    public TransactionsDTO newTransactionExpense(
        @Parameter(
            description = "Expense transaction data containing account ID and value",
            required = true,
            schema = @Schema(implementation = TransactionsBasicDTO.class),
            examples = @ExampleObject(
                value = "{\"accountID\": 1, \"value\": 50.00}"
            )
        )
        @RequestBody TransactionsBasicDTO newPar
    ) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "S");
        return transactionsService.insertTransaction(acc);
    }


    /**
     * API para incluir um lancamento de credito (income ) em conta
     * @return  TransactionsDTO
     */

    @PostMapping("/desafio/api/v1/transaction/income")
    @Operation(
        summary = "Create a new income transaction",
        description = "Records a new income (credit) transaction for the specified account. Returns the created transaction with a generated transaction ID and timestamp."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Transaction created successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = TransactionsDTO.class),
                examples = @ExampleObject(
                    value = "{\"transactionId\": 1, \"accountID\": 1, \"value\": 100.00, \"createAt\": \"2023-01-01T12:00:00\", \"type\": \"E\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Account not found",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Account with ID 1 not found\", \"errorCode\": \"NOT_FOUND\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid transaction data",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Transaction value must be positive\", \"errorCode\": \"BAD_REQUEST\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        )
    })
    public TransactionsDTO newTransactionIncome(
        @Parameter(
            description = "Income transaction data containing account ID and value",
            required = true,
            schema = @Schema(implementation = TransactionsBasicDTO.class),
            examples = @ExampleObject(
                value = "{\"accountID\": 1, \"value\": 100.00}"
            )
        )
        @RequestBody TransactionsBasicDTO newPar
    ) {
        var acc = new TransactionsDTO(newPar.getAccountID(), newPar.getValue(), "E");
        return transactionsService.insertTransaction(acc);
    }



    /**
     * API para retornar todos os lancamento de uma conta (account)
     * @param accountID : Account ID (int)
     * @return Json with list of Transactions
     */

    @GetMapping("/desafio/api/v1/transactions/{accountID}")
    @Operation(
        summary = "Get all transactions for an account",
        description = "Returns a list of all transactions (both income and expense) for the specified account."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Transactions retrieved successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = TransactionsDTO.class),
                examples = @ExampleObject(
                    value = "[{\"transactionId\": 1, \"accountID\": 1, \"value\": 100.00, \"createAt\": \"2023-01-01T12:00:00\", \"type\": \"E\"}, {\"transactionId\": 2, \"accountID\": 1, \"value\": 50.00, \"createAt\": \"2023-01-02T12:00:00\", \"type\": \"S\"}]"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Account not found",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Account with ID 1 not found\", \"errorCode\": \"NOT_FOUND\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        )
    })
    public List<TransactionsDTO> all(
        @Parameter(
            description = "ID of the account to get transactions for",
            required = true,
            example = "1"
        )
        @PathVariable Integer accountID
    ) {
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
    @Operation(
        summary = "Get transactions by date range",
        description = "Returns a list of transactions for the specified account within the given date range. Dates must be in the format dd/mm/yyyy."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Transactions retrieved successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = TransactionsDTO.class),
                examples = @ExampleObject(
                    value = "[{\"transactionId\": 1, \"accountID\": 1, \"value\": 100.00, \"createAt\": \"2023-01-01T12:00:00\", \"type\": \"E\"}, {\"transactionId\": 2, \"accountID\": 1, \"value\": 50.00, \"createAt\": \"2023-01-02T12:00:00\", \"type\": \"S\"}]"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Account not found",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Account with ID 1 not found\", \"errorCode\": \"NOT_FOUND\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid date format",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = ExceptionResponse.class),
                examples = @ExampleObject(
                    value = "{\"errorMessage\": \"Date 01-01-2023 is invalid\", \"errorCode\": \"BAD_REQUEST\", \"timestamp\": \"01-01-2023 12:00:00\"}"
                )
            )
        )
    })
    public List<TransactionsDTO> transactionsByRange(
        @Parameter(
            description = "ID of the account to get transactions for",
            required = true,
            example = "1"
        )
        @RequestParam Integer accountID,

        @Parameter(
            description = "Start date in format dd/mm/yyyy",
            required = true,
            example = "01/01/2023"
        )
        @RequestParam String beginDT,

        @Parameter(
            description = "End date in format dd/mm/yyyy",
            required = true,
            example = "31/01/2023"
        )
        @RequestParam String endDT
    ) {
        var dtStart = new StringDateDTO(beginDT);
        var dtEnd = new StringDateDTO(endDT);
        return transactionsService.listAllAccountTransactions(accountID, dtStart, dtEnd);
    }


    /**
     * API para retornar sugestões de melhorias para o projeto
     * @return Map com categorias de melhorias e suas sugestões
     */
    @GetMapping("/desafio/api/v1/improvements")
    @Operation(
        summary = "Get project improvement suggestions",
        description = "Returns a map of improvement suggestions for the project, categorized by area."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Improvement suggestions retrieved successfully",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                    value = "{\n" +
                           "  \"Code Quality\": [\"Replace @Component with @Service annotation\", \"Use constructor injection consistently\"],\n" +
                           "  \"Performance\": [\"Add indexes to frequently queried columns\", \"Consider caching for frequently accessed data\"],\n" +
                           "  \"Security\": [\"Implement authentication and authorization\", \"Add input sanitization\"]\n" +
                           "}"
                )
            )
        )
    })
    public java.util.Map<String, java.util.List<String>> getImprovements() {
        java.util.Map<String, java.util.List<String>> improvements = new java.util.HashMap<>();

        // Add categories and their suggestions
        improvements.put("Code Quality", java.util.Arrays.asList(
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

        improvements.put("Security", java.util.Arrays.asList(
            "Implement authentication and authorization (e.g., Spring Security)",
            "Add input sanitization to prevent SQL injection and XSS attacks",
            "Implement rate limiting to prevent abuse",
            "Add CORS configuration",
            "Consider using HTTPS for all communications"
        ));

        improvements.put("Documentation", java.util.Arrays.asList(
            "Add comprehensive README.md with setup instructions",
            "Document API endpoints and usage",
            "Add Javadoc comments to all public methods and classes"
        ));

        improvements.put("Testing", java.util.Arrays.asList(
            "Increase test coverage by adding tests for controllers, DAOs, and edge cases",
            "Add integration tests for API endpoints",
            "Consider implementing test containers for database tests"
        ));

        improvements.put("API Design", java.util.Arrays.asList(
            "Fix typos in endpoint paths",
            "Consider simplifying API paths by removing the verbose prefix",
            "Add input validation using Bean Validation (JSR-380)",
            "Ensure consistent use of parameter annotations across all endpoints",
            "Enhance API documentation with more detailed descriptions and examples"
        ));

        return improvements;
    }
}
