package org.mdanylko.bankingsolution.service;

import org.mdanylko.bankingsolution.dto.AccountDto;
import org.mdanylko.bankingsolution.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    AccountDto createAccount(AccountDto account) ;
    AccountDto getAccount(Long accountNumber);
    List<AccountDto> getAllAccounts();
}
