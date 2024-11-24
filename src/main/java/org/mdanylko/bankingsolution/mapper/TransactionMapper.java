package org.mdanylko.bankingsolution.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mdanylko.bankingsolution.config.MapStructConfig;
import org.mdanylko.bankingsolution.dto.SimpleTransactionDto;
import org.mdanylko.bankingsolution.dto.TransactionResponseDto;
import org.mdanylko.bankingsolution.dto.TransferTransactionDto;
import org.mdanylko.bankingsolution.entity.Transaction;

@Mapper(config = MapStructConfig.class)
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source = "account.accountNumber", target = "accountNumber")
    @Mapping(source = "transferAccount.accountNumber", target = "transferAccountNumber")
    TransactionResponseDto toDto(Transaction transaction);
    Transaction toEntity(TransactionResponseDto transactionDto);
    Transaction toEntity(SimpleTransactionDto transactionDto);
    Transaction toEntity(TransferTransactionDto transactionDto);
}
