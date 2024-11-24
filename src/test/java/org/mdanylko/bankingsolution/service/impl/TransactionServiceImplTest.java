package org.mdanylko.bankingsolution.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdanylko.bankingsolution.dto.SimpleTransactionDto;
import org.mdanylko.bankingsolution.dto.TransactionResponseDto;
import org.mdanylko.bankingsolution.dto.TransferTransactionDto;
import org.mdanylko.bankingsolution.entity.Account;
import org.mdanylko.bankingsolution.entity.Transaction;
import org.mdanylko.bankingsolution.entity.enums.TransactionType;
import org.mdanylko.bankingsolution.exception.InsufficientFoundsException;
import org.mdanylko.bankingsolution.exception.NotFoundException;
import org.mdanylko.bankingsolution.mapper.TransactionMapper;
import org.mdanylko.bankingsolution.repository.AccountRepository;
import org.mdanylko.bankingsolution.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        transactionMapper = mock(TransactionMapper.class);
        transactionService = new TransactionServiceImpl(accountRepository, transactionRepository, transactionMapper);
    }

    @Test
    void depositAddsAmountToAccountBalance() {
        SimpleTransactionDto transactionDto = new SimpleTransactionDto();
        transactionDto.setAccountNumber(123456789012L);
        transactionDto.setAmount(BigDecimal.valueOf(500));

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(BigDecimal.valueOf(500));
        transaction.setAccount(account);
        transaction.setBalance(BigDecimal.valueOf(1500));
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionTimestamp(LocalDateTime.now());

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(new TransactionResponseDto());

        TransactionResponseDto response = transactionService.deposit(transactionDto);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(1500), account.getBalance());
    }

    @Test
    void depositThrowsNotFoundExceptionForNonExistentAccount() {
        SimpleTransactionDto transactionDto = new SimpleTransactionDto();
        transactionDto.setAccountNumber(123456789012L);

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.deposit(transactionDto));
    }

    @Test
    void withdrawSubtractsAmountFromAccountBalance() {
        SimpleTransactionDto transactionDto = new SimpleTransactionDto();
        transactionDto.setAccountNumber(123456789012L);
        transactionDto.setAmount(BigDecimal.valueOf(500));

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(BigDecimal.valueOf(500));
        transaction.setAccount(account);
        transaction.setBalance(BigDecimal.valueOf(500));
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionTimestamp(LocalDateTime.now());

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(new TransactionResponseDto());

        TransactionResponseDto response = transactionService.withdraw(transactionDto);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(500), account.getBalance());
    }

    @Test
    void withdrawThrowsNotFoundExceptionForNonExistentAccount() {
        SimpleTransactionDto transactionDto = new SimpleTransactionDto();
        transactionDto.setAccountNumber(123456789012L);

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.withdraw(transactionDto));
    }

    @Test
    void withdrawThrowsInsufficientFoundsExceptionForInsufficientBalance() {
        SimpleTransactionDto transactionDto = new SimpleTransactionDto();
        transactionDto.setAccountNumber(123456789012L);
        transactionDto.setAmount(BigDecimal.valueOf(1500));

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.of(account));

        assertThrows(InsufficientFoundsException.class, () -> transactionService.withdraw(transactionDto));
    }

    @Test
    void transferMovesAmountBetweenAccounts() {
        TransferTransactionDto transactionDto = new TransferTransactionDto();
        transactionDto.setAccountNumber(123456789012L);
        transactionDto.setTransferAccountNumber(987654321098L);
        transactionDto.setAmount(BigDecimal.valueOf(500));

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        Account transferAccount = new Account();
        transferAccount.setBalance(BigDecimal.valueOf(2000));

        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(BigDecimal.valueOf(500));
        transaction.setAccount(account);
        transaction.setTransferAccount(transferAccount);
        transaction.setBalance(BigDecimal.valueOf(500));
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionTimestamp(LocalDateTime.now());

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.of(account));
        when(accountRepository.findByAccountNumber(transactionDto.getTransferAccountNumber())).thenReturn(Optional.of(transferAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(new TransactionResponseDto());

        TransactionResponseDto response = transactionService.transfer(transactionDto);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(500), account.getBalance());
        assertEquals(BigDecimal.valueOf(2500), transferAccount.getBalance());
    }

    @Test
    void transferThrowsNotFoundExceptionForNonExistentSourceAccount() {
        TransferTransactionDto transactionDto = new TransferTransactionDto();
        transactionDto.setAccountNumber(123456789012L);
        transactionDto.setTransferAccountNumber(987654321098L);

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.transfer(transactionDto));
    }

    @Test
    void transferThrowsNotFoundExceptionForNonExistentDestinationAccount() {
        TransferTransactionDto transactionDto = new TransferTransactionDto();
        transactionDto.setAccountNumber(123456789012L);
        transactionDto.setTransferAccountNumber(987654321098L);

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.of(account));
        when(accountRepository.findByAccountNumber(transactionDto.getTransferAccountNumber())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.transfer(transactionDto));
    }

    @Test
    void transferThrowsInsufficientFoundsExceptionForInsufficientBalance() {
        TransferTransactionDto transactionDto = new TransferTransactionDto();
        transactionDto.setAccountNumber(123456789012L);
        transactionDto.setTransferAccountNumber(987654321098L);
        transactionDto.setAmount(BigDecimal.valueOf(1500));

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        Account transferAccount = new Account();
        transferAccount.setBalance(BigDecimal.valueOf(2000));

        when(accountRepository.findByAccountNumber(transactionDto.getAccountNumber())).thenReturn(Optional.of(account));
        when(accountRepository.findByAccountNumber(transactionDto.getTransferAccountNumber())).thenReturn(Optional.of(transferAccount));

        assertThrows(InsufficientFoundsException.class, () -> transactionService.transfer(transactionDto));
    }
}