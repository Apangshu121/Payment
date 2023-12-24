package com.example.demo.service;

import com.example.demo.models.OfflineTransactionRequest;
import com.example.demo.models.OnlineTransactionRequest;
import com.example.demo.models.OnlineTransactionResponse;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    OnlineTransactionResponse onlineTransaction(OnlineTransactionRequest onlineTransactionRequest);
    String onlineTransactionVerify(OnlineTransactionResponse onlineTransactionResponse);
    String offlineTransaction(OfflineTransactionRequest offlineTransactionRequest);

}
