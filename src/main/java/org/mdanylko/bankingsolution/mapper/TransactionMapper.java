package org.mdanylko.bankingsolution.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mdanylko.bankingsolution.config.MapStructConfig;
import org.mdanylko.bankingsolution.dto.TransactionDto;
import org.mdanylko.bankingsolution.entity.Transaction;

@Mapper(config = MapStructConfig.class)
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
}
