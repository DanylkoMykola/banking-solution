package org.mdanylko.bankingsolution.service.impl;

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
import org.springframework.transaction.annotation.Transactional;
import org.mdanylko.bankingsolution.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mdanylko.bankingsolution.util.TextConstants.ACCOUNT_NOT_FOUND;
import static org.mdanylko.bankingsolution.util.TextConstants.INSUFFICIENT_FUNDS;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponseDto deposit(SimpleTransactionDto transactionDto) {
        Account account = accountRepository.findByAccountNumber(transactionDto.getAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND));
        account.addBalance(transactionDto.getAmount());

        Transaction transaction = Transaction.builder()
                .transactionAmount(transactionDto.getAmount())
                .account(account)
                .balance(account.getBalance())
                .transactionType(TransactionType.DEPOSIT)
                .transactionTimestamp(LocalDateTime.now())
                .build();
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    public TransactionResponseDto withdraw(SimpleTransactionDto transactionDto) {
        Account account = accountRepository.findByAccountNumber(transactionDto.getAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND));
        account.subtractBalance(transactionDto.getAmount());

        Transaction transaction = Transaction.builder()
                .transactionAmount(transactionDto.getAmount())
                .account(account)
                .balance(account.getBalance())
                .transactionType(TransactionType.WITHDRAWAL)
                .transactionTimestamp(LocalDateTime.now())
                .build();
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    public TransactionResponseDto transfer(TransferTransactionDto transactionDto) {
        Account account = accountRepository.findByAccountNumber(transactionDto.getAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND + " " + transactionDto.getAccountNumber()));
        Account transferAccount = accountRepository.findByAccountNumber(transactionDto.getTransferAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND + " " + transactionDto.getTransferAccountNumber()));

        account.subtractBalance(transactionDto.getAmount());
        transferAccount.addBalance(transactionDto.getAmount());

        Transaction transaction = Transaction.builder()
                .transactionAmount(transactionDto.getAmount())
                .account(account)
                .transferAccount(transferAccount)
                .balance(account.getBalance())
                .transactionType(TransactionType.TRANSFER)
                .transactionTimestamp(LocalDateTime.now())
                .build();
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }
}

