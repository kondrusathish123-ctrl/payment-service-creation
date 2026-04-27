package com.dilipIT.controller;

import com.dilipIT.Request.AccountRequest;
import com.dilipIT.entity.Account;
import com.dilipIT.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest request) {
        Account account = accountService.createAccount(request);
        return ResponseEntity.ok("Account created successfully with ID: " + account.getId());
    }
}
