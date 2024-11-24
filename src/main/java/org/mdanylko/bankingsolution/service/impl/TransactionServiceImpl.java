package org.mdanylko.bankingsolution.service.impl;

import org.mdanylko.bankingsolution.dto.TransactionDto;
import org.mdanylko.bankingsolution.entity.Account;
import org.mdanylko.bankingsolution.entity.Transaction;
import org.mdanylko.bankingsolution.exception.InsufficientFoundsException;
import org.mdanylko.bankingsolution.exception.NotFoundException;
import org.mdanylko.bankingsolution.mapper.TransactionMapper;
import org.mdanylko.bankingsolution.repository.AccountRepository;
import org.mdanylko.bankingsolution.repository.TransactionRepository;
import org.mdanylko.bankingsolution.util.TextConstants;
import org.springframework.transaction.annotation.Transactional;
import org.mdanylko.bankingsolution.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public TransactionDto deposit(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.toEntity(transactionDto);
        Account account = accountRepository.findByAccountNumber(transaction.getTransferAccount().getAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND));
        BigDecimal newBalance = account.getBalance().add(transaction.getTransactionAmount());
        account.setBalance(newBalance);
        transaction.setTransferAccount(account);
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    public TransactionDto withdraw(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.toEntity(transactionDto);
        Account account = accountRepository.findByAccountNumber(transaction.getAccount().getAccountNumber())
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND));
        if (account.getBalance().compareTo(transaction.getTransactionAmount()) < 0) {
            throw new InsufficientFoundsException(INSUFFICIENT_FUNDS);
        }
        BigDecimal newBalance = account.getBalance().subtract(transaction.getTransactionAmount());
        account.setBalance(newBalance);
        transaction.setAccount(account);
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    public TransactionDto transfer(TransactionDto transactionDto) {
        withdraw(transactionDto);
        return deposit(transactionDto);
    }
}

