package com.example.demo.payment.repository;

import com.example.demo.payment.models.VendorWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorWalletRepository extends JpaRepository<VendorWallet, Long> {
    Optional<VendorWallet> findByVendorId(int vendorId);
}
