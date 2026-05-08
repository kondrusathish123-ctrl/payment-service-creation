package com.dilipIT.service;

import com.dilipIT.Repository.AccountRepository;
import com.dilipIT.Request.NetBankingInformation;
import com.dilipIT.entity.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NetBankingServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private NetBankingService netBankingService;

    @Test
    void testPaymentSuccess() {
        Account account = new Account();
        account.setUserName("dilip");
        account.setPassword("secret123");
        account.setBalance(5000.00);

        NetBankingInformation info = new NetBankingInformation("dilip", "secret123", 1000.00);

        Mockito.when(accountRepository.findByUserName("dilip"))
                .thenReturn(Optional.of(account));

        String result = netBankingService.processPayment(info);

        assertTrue(result.startsWith("Payment Success"));
        assertEquals(4000.00, account.getBalance());
        Mockito.verify(accountRepository).save(account);
    }

    @Test
    void testInvalidUsername() {
        NetBankingInformation info = new NetBankingInformation("wrong", "secret123", 1000.00);

        Mockito.when(accountRepository.findByUserName("wrong"))
                .thenReturn(Optional.empty());

        String result = netBankingService.processPayment(info);

        assertEquals("Payment Failed: Invalid credentials", result);
    }

    @Test
    void testInvalidPassword() {
        Account account = new Account();
        account.setUserName("dilip");
        account.setPassword("secret123");
        account.setBalance(5000.00);

        NetBankingInformation info = new NetBankingInformation("dilip", "badpass", 1000.00);

        Mockito.when(accountRepository.findByUserName("dilip"))
                .thenReturn(Optional.of(account));

        String result = netBankingService.processPayment(info);

        assertEquals("Payment Failed: Invalid credentials", result);
    }

    @Test
    void testInsufficientBalance() {
        Account account = new Account();
        account.setUserName("dilip");
        account.setPassword("secret123");
        account.setBalance(500.00);

        NetBankingInformation info = new NetBankingInformation("dilip", "secret123", 1000.00);

        Mockito.when(accountRepository.findByUserName("dilip"))
                .thenReturn(Optional.of(account));

        String result = netBankingService.processPayment(info);

        assertEquals("Payment Failed: Insufficient balance", result);
    }
}
