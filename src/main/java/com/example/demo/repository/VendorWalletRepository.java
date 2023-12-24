package com.example.demo.repository;

import com.example.demo.models.VendorWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorWalletRepository extends JpaRepository<VendorWallet, Long> {
    Optional<VendorWallet> findByVendorId(int vendorId);
}
