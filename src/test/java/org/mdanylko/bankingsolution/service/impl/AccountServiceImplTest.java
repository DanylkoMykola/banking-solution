package org.mdanylko.bankingsolution.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdanylko.bankingsolution.dto.AccountDto;
import org.mdanylko.bankingsolution.entity.Account;
import org.mdanylko.bankingsolution.exception.NotFoundException;
import org.mdanylko.bankingsolution.mapper.AccountMapper;
import org.mdanylko.bankingsolution.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountMapper = mock(AccountMapper.class);
        accountService = new AccountServiceImpl(accountRepository, accountMapper);
    }

    @Test
    void createAccountSavesAndReturnsAccountDto() {
        AccountDto accountDto = getAccountDto();
        Account account = getAccount();


        when(accountMapper.toEntity(accountDto)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(accountDto);

        assertEquals(accountDto, result);
        verify(accountRepository).save(account);
    }

    @Test
    void getAccountReturnsAccountDtoForExistingAccount() {
        Long accountNumber = 123456789012L;
        Account account = getAccount();
        AccountDto accountDto = getAccountDto();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getAccount(accountNumber);

        assertEquals(accountDto, result);
    }

    @Test
    void getAccountThrowsNotFoundExceptionForNonExistentAccount() {
        Long accountNumber = 123456789015L;

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getAccount(accountNumber));
    }

    @Test
    void getAllAccountsReturnsListOfAccountDtos() {
        Account account = getAccount();
        AccountDto accountDto = getAccountDto();
        List<Account> accounts = Collections.singletonList(account);
        List<AccountDto> accountDtos = Collections.singletonList(accountDto);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        List<AccountDto> result = accountService.getAllAccounts();

        assertEquals(accountDtos, result);
    }

    @Test
    void getAllAccountsReturnsEmptyListWhenNoAccountsExist() {
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        List<AccountDto> result = accountService.getAllAccounts();

        assertTrue(result.isEmpty());
    }

    private Account getAccount() {
        return new Account(123456789012L, "John Doe", BigDecimal.TEN);
    }

    private AccountDto getAccountDto() {
        return new AccountDto(1L, 123456789012L, "John Doe", BigDecimal.TEN);
    }
}