package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnlineTransactionResponse {
    private int paymentId;
    private String otp;
    private int userId;
    private int vendorId;
}
