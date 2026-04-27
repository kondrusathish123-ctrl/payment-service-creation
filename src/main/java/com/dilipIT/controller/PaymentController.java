package com.dilipIT.controller;


import com.dilipIT.service.NetBankingService;
import com.dilipIT.Request.NetBankingInformation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController {

    private final NetBankingService netBankingService;

    public PaymentController(NetBankingService netBankingService) {
        this.netBankingService = netBankingService;
    }

    //NetBanking Service
    @PostMapping("/payment/netbanking")
    public ResponseEntity<String> netBankingPayment(@RequestBody NetBankingInformation netBankingInformation){
        String result = netBankingService.processPayment(netBankingInformation);
        return ResponseEntity.ok(result);
    }
}
