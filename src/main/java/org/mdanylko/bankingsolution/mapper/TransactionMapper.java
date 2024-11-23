package org.mdanylko.bankingsolution.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mdanylko.bankingsolution.dto.TransactionDto;
import org.mdanylko.bankingsolution.entity.Transaction;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
}
