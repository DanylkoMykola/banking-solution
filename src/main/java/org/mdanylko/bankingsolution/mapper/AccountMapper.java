package org.mdanylko.bankingsolution.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mdanylko.bankingsolution.dto.AccountDto;
import org.mdanylko.bankingsolution.entity.Account;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
}
