package com.pra.desafio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pra.desafio.dto.*;
import com.pra.desafio.service.AccountService;
import com.pra.desafio.service.TransactionsService;
import com.pra.desafio.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DesafioController.class)
public class DesafioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionsService transactionsService;

    @MockBean
    private UsersService userService;

    @MockBean
    private AccountService accountService;

    @Test
    public void testNewUser() throws Exception {
        // Prepare test data
        UsersBasicDTO newUser = new UsersBasicDTO();
        newUser.setName("Test User");
        newUser.setEmail("test@example.com");

        UsersDTO expectedResponse = new UsersDTO(1, "test@example.com", "Test User");

        // Mock service response
        when(userService.insertUser(any(UsersDTO.class))).thenReturn(expectedResponse);

        // Perform request and validate response
        mockMvc.perform(post("/desafio/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testGetAccountBalance() throws Exception {
        // Mock service response
        when(accountService.getAccountBalance(1)).thenReturn(BigDecimal.valueOf(1000.50));

        // Perform request and validate response
        mockMvc.perform(get("/desafio/api/v1/accountBalace/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1000.50));
    }

    @Test
    public void testNewAccount() throws Exception {
        // Prepare test data
        AccountsBasicDTO newAccount = new AccountsBasicDTO(1);

        AccountsDTO expectedResponse = new AccountsDTO(newAccount);
        expectedResponse.setAccountId(1);
        expectedResponse.setBalance(BigDecimal.valueOf(0.00));
        expectedResponse.setIncome(BigDecimal.valueOf(0.00));
        expectedResponse.setExpense(BigDecimal.valueOf(0.00));

        // Mock service response
        when(accountService.insertAccount(any(AccountsDTO.class))).thenReturn(expectedResponse);

        // Perform request and validate response
        mockMvc.perform(post("/desafio/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.balance").value(0.00))
                .andExpect(jsonPath("$.income").value(0.00))
                .andExpect(jsonPath("$.expense").value(0.00));
    }

    @Test
    public void testNewTransactionExpense() throws Exception {
        // Prepare test data
        TransactionsBasicDTO newTransaction = new TransactionsBasicDTO();
        newTransaction.setAccountID(1);
        newTransaction.setValue(BigDecimal.valueOf(100.00));

        TransactionsDTO expectedResponse = new TransactionsDTO(1, BigDecimal.valueOf(100.00), "S");
        expectedResponse.setTransactionId(1);
        expectedResponse.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));

        // Mock service response
        when(transactionsService.insertTransaction(any(TransactionsDTO.class))).thenReturn(expectedResponse);

        // Perform request and validate response
        mockMvc.perform(post("/desafio/api/v1/transaction/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.accountID").value(1))
                .andExpect(jsonPath("$.value").value(100.00))
                .andExpect(jsonPath("$.type").value("S"));
    }

    @Test
    public void testNewTransactionIncome() throws Exception {
        // Prepare test data
        TransactionsBasicDTO newTransaction = new TransactionsBasicDTO();
        newTransaction.setAccountID(1);
        newTransaction.setValue(BigDecimal.valueOf(200.00));

        TransactionsDTO expectedResponse = new TransactionsDTO(1, BigDecimal.valueOf(200.00), "E");
        expectedResponse.setTransactionId(1);
        expectedResponse.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));

        // Mock service response
        when(transactionsService.insertTransaction(any(TransactionsDTO.class))).thenReturn(expectedResponse);

        // Perform request and validate response
        mockMvc.perform(post("/desafio/api/v1/transaction/income")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.accountID").value(1))
                .andExpect(jsonPath("$.value").value(200.00))
                .andExpect(jsonPath("$.type").value("E"));
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        // Prepare test data
        TransactionsDTO transaction1 = new TransactionsDTO(1, BigDecimal.valueOf(100.00), "S");
        transaction1.setTransactionId(1);
        transaction1.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));

        TransactionsDTO transaction2 = new TransactionsDTO(1, BigDecimal.valueOf(200.00), "E");
        transaction2.setTransactionId(2);
        transaction2.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));

        List<TransactionsDTO> transactions = Arrays.asList(transaction1, transaction2);

        // Mock service response
        when(transactionsService.listAllAccountTransactions(1)).thenReturn(transactions);

        // Perform request and validate response
        mockMvc.perform(get("/desafio/api/v1/transanctions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId").value(1))
                .andExpect(jsonPath("$[0].accountID").value(1))
                .andExpect(jsonPath("$[0].value").value(100.00))
                .andExpect(jsonPath("$[0].type").value("S"))
                .andExpect(jsonPath("$[1].transactionId").value(2))
                .andExpect(jsonPath("$[1].accountID").value(1))
                .andExpect(jsonPath("$[1].value").value(200.00))
                .andExpect(jsonPath("$[1].type").value("E"));
    }

    @Test
    public void testGetTransactionsByRange() throws Exception {
        // Prepare test data
        TransactionsDTO transaction1 = new TransactionsDTO(1, BigDecimal.valueOf(100.00), "S");
        transaction1.setTransactionId(1);
        transaction1.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));

        TransactionsDTO transaction2 = new TransactionsDTO(1, BigDecimal.valueOf(200.00), "E");
        transaction2.setTransactionId(2);
        transaction2.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));

        List<TransactionsDTO> transactions = Arrays.asList(transaction1, transaction2);

        // Mock service response
        when(transactionsService.listAllAccountTransactions(any(Integer.class), any(StringDateDTO.class), any(StringDateDTO.class)))
                .thenReturn(transactions);

        // Perform request and validate response
        mockMvc.perform(get("/desafio/api/v1/transactiosByRange")
                .param("accountID", "1")
                .param("beginDT", "01/01/2023")
                .param("endDT", "31/12/2023"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId").value(1))
                .andExpect(jsonPath("$[0].accountID").value(1))
                .andExpect(jsonPath("$[0].value").value(100.00))
                .andExpect(jsonPath("$[0].type").value("S"))
                .andExpect(jsonPath("$[1].transactionId").value(2))
                .andExpect(jsonPath("$[1].accountID").value(1))
                .andExpect(jsonPath("$[1].value").value(200.00))
                .andExpect(jsonPath("$[1].type").value("E"));
    }
}