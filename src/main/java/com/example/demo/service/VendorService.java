package com.example.demo.service;

import com.example.demo.payment.models.TransferFundsToPersonal;
import com.example.demo.payment.models.User;
import org.springframework.stereotype.Service;

@Service
public interface VendorService {
    String addVendor(User vendor);
    String transferFundsToPersonalWallet(TransferFundsToPersonal funds);
}
