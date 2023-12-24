package com.example.demo.repository;

import com.example.demo.models.VendorPersonalWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorPersonalWalletRepository extends JpaRepository<VendorPersonalWallet, Long> {
    Optional<VendorPersonalWallet> findByVendorId(int vendorId);
}
