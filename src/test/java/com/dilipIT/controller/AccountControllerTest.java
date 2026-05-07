package com.dilipIT.controller;

import com.dilipIT.Request.AccountRequest;
import com.dilipIT.entity.Account;
import com.dilipIT.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import(AccountControllerTest.TestConfig.class)  // Import custom test config
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    static class TestConfig {
        @Bean
        AccountService accountService() {
            return Mockito.mock(AccountService.class);
        }
    }

    @Test
    void testCreateAccountSuccess() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setUserName("dilip");
        account.setPassword("secret123");
        account.setBalance(5000.00);

        Mockito.when(accountService.createAccount(Mockito.any(AccountRequest.class)))
                .thenReturn(account);

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"dilip\",\"password\":\"secret123\",\"balance\":5000.00}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account created successfully with ID: 1"));


    }



}
