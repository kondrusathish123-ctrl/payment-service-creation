package com.dilipIT.service;

import com.dilipIT.Repository.AccountRepository;
import com.dilipIT.Request.AccountRequest;
import com.dilipIT.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(AccountRequest request) {
        Account account = new Account();
        account.setUserName(request.getUserName());
        account.setPassword(request.getPassword());
        account.setBalance(request.getBalance());
        return accountRepository.save(account);
    }
}
