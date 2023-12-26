package com.example.demo.service;

import com.example.demo.payment.models.OfflineTransactionRequest;
import com.example.demo.payment.models.OnlineTransactionRequest;
import com.example.demo.payment.models.OnlineTransactionResponse;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    OnlineTransactionResponse onlineTransaction(OnlineTransactionRequest onlineTransactionRequest);
    String onlineTransactionVerify(OnlineTransactionResponse onlineTransactionResponse);
    String offlineTransaction(OfflineTransactionRequest offlineTransactionRequest);

}
