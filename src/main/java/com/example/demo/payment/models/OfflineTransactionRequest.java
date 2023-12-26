package com.example.demo.payment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfflineTransactionRequest {

    private String vendorId;
    private double amount;
    private String code;
    private double gpsLocationX;
    private double gpsLocationY;
}
