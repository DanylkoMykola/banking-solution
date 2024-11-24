package org.mdanylko.bankingsolution.service;

import org.mdanylko.bankingsolution.dto.SimpleTransactionDto;
import org.mdanylko.bankingsolution.dto.TransactionResponseDto;
import org.mdanylko.bankingsolution.dto.TransferTransactionDto;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    TransactionResponseDto deposit(SimpleTransactionDto transactionDto);
    TransactionResponseDto withdraw(SimpleTransactionDto transactionDto);
    TransactionResponseDto transfer(TransferTransactionDto transactionDto);
}
