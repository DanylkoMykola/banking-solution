package org.mdanylko.bankingsolution.service;

import org.mdanylko.bankingsolution.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface TransactionService {
    TransactionDto deposit(TransactionDto transactionDto);
    TransactionDto withdraw(TransactionDto transactionDto);
    TransactionDto transfer(TransactionDto transactionDto);
}
