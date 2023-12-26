package com.example.demo.payment.repository;

import com.example.demo.payment.models.VendorPersonalWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorPersonalWalletRepository extends JpaRepository<VendorPersonalWallet, Long> {
    Optional<VendorPersonalWallet> findByVendorId(int vendorId);
}
