package org.mdanylko.bankingsolution.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdanylko.bankingsolution.dto.AccountDto;
import org.mdanylko.bankingsolution.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    private AccountService accountService;
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountService.class);
        accountController = new AccountController(accountService);
    }

    @Test
    void createAccountReturnsCreatedStatus() {
        AccountDto accountDto = new AccountDto();
        accountDto.setOwnerName("John Doe");
        accountDto.setBalance(BigDecimal.valueOf(1000));

        when(accountService.createAccount(accountDto)).thenReturn(accountDto);

        ResponseEntity<AccountDto> response = accountController.createAccount(accountDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }

    @Test
    void getAccountReturnsAccountDetails() {
        Long accountNumber = 123456789012L;
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(accountNumber);

        when(accountService.getAccount(accountNumber)).thenReturn(accountDto);

        ResponseEntity<AccountDto> response = accountController.getAccount(accountNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }

    @Test
    void getAllAccountsReturnsListOfAccounts() {
        AccountDto accountDto = new AccountDto();
        List<AccountDto> accounts = Collections.singletonList(accountDto);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        ResponseEntity<List<AccountDto>> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accounts, response.getBody());
    }

    @Test
    void getAllAccountsReturnsEmptyListWhenNoAccountsExist() {
        when(accountService.getAllAccounts()).thenReturn(Collections.emptyList());

        ResponseEntity<List<AccountDto>> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }
}