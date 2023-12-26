package com.example.demo.controller;

import com.example.demo.payment.models.TransferFundsToPersonal;
import com.example.demo.payment.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    VendorService vendorService;

    @PostMapping("/register")
    public String addVendor(@RequestBody User vendor) {
        return vendorService.addVendor(vendor);
    }

    @PostMapping("/transferFundsToPersonalWallet")
    public String transferFundsToPersonalWallet(@RequestBody TransferFundsToPersonal transferFunds) {
        return vendorService.transferFundsToPersonalWallet(transferFunds);
    }

}
