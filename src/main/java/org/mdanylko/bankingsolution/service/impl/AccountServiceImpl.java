package org.mdanylko.bankingsolution.service.impl;

import org.mdanylko.bankingsolution.dto.AccountDto;
import org.mdanylko.bankingsolution.exception.NotFoundException;
import org.mdanylko.bankingsolution.mapper.AccountMapper;

import org.springframework.transaction.annotation.Transactional;
import org.mdanylko.bankingsolution.entity.Account;
import org.mdanylko.bankingsolution.repository.AccountRepository;
import org.mdanylko.bankingsolution.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto getAccount(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .toList();
    }
}
