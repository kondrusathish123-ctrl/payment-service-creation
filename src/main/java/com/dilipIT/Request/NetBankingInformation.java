package com.dilipIT.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetBankingInformation {

    private  String userName;
    private String password;
    private double amountTobePaid;


}
