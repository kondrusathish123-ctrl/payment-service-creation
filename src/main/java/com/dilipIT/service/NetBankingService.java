package com.dilipIT.service;


import com.dilipIT.Repository.AccountRepository;
import com.dilipIT.Request.NetBankingInformation;
import com.dilipIT.entity.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NetBankingService {

    private final AccountRepository accountRepository;
    public NetBankingService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String processPayment(NetBankingInformation info){
        Optional<Account> optionalAccount= accountRepository.findByUserName(info.getUserName());
        if (optionalAccount.isEmpty()) {
            return "Payment Failed: Invalid credentials";
        }

        Account account = optionalAccount.get();
        if (!account.getPassword().equals(info.getPassword())) {
            return "Payment Failed: Invalid credentials";
        }

        if (info.getAmountTobePaid() > account.getBalance()) {
            return "Payment Failed: Insufficient balance";
        }
        account.setBalance(account.getBalance() - info.getAmountTobePaid());
        accountRepository.save(account);
        return "Payment Success. Transaction ID: " + UUID.randomUUID();
    }
}
