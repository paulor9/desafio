package com.pra.desafio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pra.desafio.DesafioApplication;
import com.pra.desafio.dto.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DesafioApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DesafioControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static int userId;
    private static int accountId;
    private static int transactionId;

    @Test
    @Order(1)
    public void testCreateUser() throws Exception {
        // Prepare test data with unique email using timestamp
        String uniqueEmail = "integration_" + System.currentTimeMillis() + "@example.com";
        UsersBasicDTO newUser = new UsersBasicDTO();
        newUser.setName("Integration Test User");
        newUser.setEmail(uniqueEmail);

        // Perform request and capture response
        MvcResult result = mockMvc.perform(post("/desafio/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Test User"))
                .andExpect(jsonPath("$.email").value(uniqueEmail))
                .andReturn();

        // Extract userId for later tests
        String responseContent = result.getResponse().getContentAsString();
        UsersDTO createdUser = objectMapper.readValue(responseContent, UsersDTO.class);
        userId = createdUser.getUserId();
    }

    @Test
    @Order(2)
    public void testCreateAccount() throws Exception {
        // Prepare test data
        AccountsBasicDTO newAccount = new AccountsBasicDTO(userId);

        // Perform request and capture response
        MvcResult result = mockMvc.perform(post("/desafio/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(userId))
                .andExpect(jsonPath("$.balance").value(0.00))
                .andExpect(jsonPath("$.income").value(0.00))
                .andExpect(jsonPath("$.expense").value(0.00))
                .andReturn();

        // Extract accountId for later tests
        String responseContent = result.getResponse().getContentAsString();
        AccountsDTO createdAccount = objectMapper.readValue(responseContent, AccountsDTO.class);
        accountId = createdAccount.getAccountId();
    }

    @Test
    @Order(3)
    public void testAddIncomeTransaction() throws Exception {
        // Prepare test data
        TransactionsBasicDTO newTransaction = new TransactionsBasicDTO();
        newTransaction.setAccountID(accountId);
        newTransaction.setValue(BigDecimal.valueOf(500.00));

        // Perform request and capture response
        MvcResult result = mockMvc.perform(post("/desafio/api/v1/transaction/income")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountID").value(accountId))
                .andExpect(jsonPath("$.value").value(500.00))
                .andExpect(jsonPath("$.type").value("E"))
                .andReturn();

        // Extract transactionId for later reference
        String responseContent = result.getResponse().getContentAsString();
        TransactionsDTO createdTransaction = objectMapper.readValue(responseContent, TransactionsDTO.class);
        transactionId = createdTransaction.getTransactionId();
    }

    @Test
    @Order(4)
    public void testAddExpenseTransaction() throws Exception {
        // Prepare test data
        TransactionsBasicDTO newTransaction = new TransactionsBasicDTO();
        newTransaction.setAccountID(accountId);
        newTransaction.setValue(BigDecimal.valueOf(200.00));

        // Perform request
        mockMvc.perform(post("/desafio/api/v1/transaction/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountID").value(accountId))
                .andExpect(jsonPath("$.value").value(200.00))
                .andExpect(jsonPath("$.type").value("S"));
    }

    @Test
    @Order(5)
    public void testGetAccountBalance() throws Exception {
        // Perform request - balance should be 500 (income) - 200 (expense) = 300
        mockMvc.perform(get("/desafio/api/v1/accountBalace/" + accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(300.00));
    }

    @Test
    @Order(6)
    public void testGetAllTransactions() throws Exception {
        // Perform request - should return 2 transactions
        mockMvc.perform(get("/desafio/api/v1/transanctions/" + accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountID").value(accountId))
                .andExpect(jsonPath("$[1].accountID").value(accountId));
    }

    @Test
    @Order(7)
    public void testGetTransactionsByDateRange() throws Exception {
        // Get current date in format dd/MM/yyyy
        String today = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String tomorrow = java.time.LocalDate.now().plusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Perform request with date range including today
        mockMvc.perform(get("/desafio/api/v1/transactiosByRange")
                .param("accountID", String.valueOf(accountId))
                .param("beginDT", today)
                .param("endDT", tomorrow))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountID").value(accountId))
                .andExpect(jsonPath("$[1].accountID").value(accountId));
    }

    @Test
    @Order(8)
    public void testGetTransactionsByPastDateRange() throws Exception {
        // Get date from last year
        String lastYear = java.time.LocalDate.now().minusYears(1).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String lastYearPlusDay = java.time.LocalDate.now().minusYears(1).plusDays(1).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Perform request with date range from last year (should return empty array)
        mockMvc.perform(get("/desafio/api/v1/transactiosByRange")
                .param("accountID", String.valueOf(accountId))
                .param("beginDT", lastYear)
                .param("endDT", lastYearPlusDay))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
