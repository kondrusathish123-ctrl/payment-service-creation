package com.dilipIT.controller;

import com.dilipIT.Request.NetBankingInformation;
import com.dilipIT.service.NetBankingService;
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

@WebMvcTest(PaymentController.class)
@Import(PaymentControllerTest.TestConfig.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NetBankingService netBankingService;

    static class TestConfig {
        @Bean
        NetBankingService netBankingService() {
            return Mockito.mock(NetBankingService.class);
        }
    }

    @Test
    void testNetBankingPaymentSuccess() throws Exception {
        Mockito.when(netBankingService.processPayment(Mockito.any(NetBankingInformation.class)))
                .thenReturn("Payment Success. Transaction ID: 12345");

        mockMvc.perform(post("/payment/netbanking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"dilip\",\"password\":\"secret123\",\"amountTobePaid\":1000.00}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Success. Transaction ID: 12345"));
    }

    @Test
    void testNetBankingPaymentInvalidCredentials() throws Exception {
        Mockito.when(netBankingService.processPayment(Mockito.any(NetBankingInformation.class)))
                .thenReturn("Payment Failed: Invalid credentials");

        mockMvc.perform(post("/payment/netbanking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"wrong\",\"password\":\"bad\",\"amountTobePaid\":1000.00}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Failed: Invalid credentials"));
    }

    @Test
    void testNetBankingPaymentInsufficientBalance() throws Exception {
        Mockito.when(netBankingService.processPayment(Mockito.any(NetBankingInformation.class)))
                .thenReturn("Payment Failed: Insufficient balance");

        mockMvc.perform(post("/payment/netbanking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"dilip\",\"password\":\"secret123\",\"amountTobePaid\":999999.00}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Failed: Insufficient balance"));
    }

    @Test
    void testNetBankingPaymentMalformedJson() throws Exception {
        mockMvc.perform(post("/payment/netbanking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{userName:dilip,password:secret123}")) // invalid JSON
                .andExpect(status().isBadRequest());
    }
}
