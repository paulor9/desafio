package com.pra.desafio.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountsDTO extends AccountsBasicDTO {
    private int accountId;
    protected BigDecimal balance;
    protected BigDecimal income;
    protected BigDecimal expense;


    public AccountsDTO() {
       super();
    }

    public AccountsDTO( AccountsBasicDTO newPar ) {
        accountId = 0;
        balance = BigDecimal.valueOf(0.00);
        expense = BigDecimal.valueOf(0.00);
        income = BigDecimal.valueOf(0.00);
        this.userID = newPar.getUserID();
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId( int accountId ) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance( BigDecimal balance ) {
        this.balance = balance;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome( BigDecimal income ) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense( BigDecimal expense ) {
        this.expense = expense;
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountsDTO that = (AccountsDTO) o;
        return accountId == that.accountId && Objects.equals(balance, that.balance) && Objects.equals(income, that.income) && Objects.equals(expense, that.expense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance, income, expense);
    }




}
