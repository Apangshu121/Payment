package com.example.demo.serviceImpl;

import com.example.demo.payment.models.*;
import com.example.demo.payment.repository.TransactionRepository;
import com.example.demo.payment.repository.UserWalletRepository;
import com.example.demo.payment.repository.VendorWalletRepository;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.demo.payment.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;
    private final VendorWalletRepository vendorWalletRepository;



        @Override
        public OnlineTransactionResponse onlineTransaction(OnlineTransactionRequest onlineTransactionRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = ((UserDetails)principal).getUsername();
        String vendorId = onlineTransactionRequest.getVendorId();

        User user = userRepository.findByUserId(userId).orElseThrow();
        User vendor = userRepository.findByUserId(vendorId).orElseThrow();

        UserWallet userWallet = userWalletRepository.findByUserId(user.getId()).orElseThrow();

        if(userWallet.getBalance() < onlineTransactionRequest.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        String otp = generateOtp();

        var transaction = Transaction.builder()
                    .user1(user.getUserId())
                    .user2(vendor.getUserId())
                    .date(LocalDateTime.now())
                    .amount(onlineTransactionRequest.getAmount())
                    .status("pending")
                    .otp(otp)
                    .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        var transactionResponse = OnlineTransactionResponse.builder()
                .paymentId(savedTransaction.getId())
                .otp(otp)
                .vendorId(vendor.getId())
                .build();

        return transactionResponse;

    }

    @Override
    public String onlineTransactionVerify(OnlineTransactionResponse onlineTransactionResponse){

        Transaction transaction = transactionRepository.findById(onlineTransactionResponse.getPaymentId()).orElseThrow();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = ((UserDetails)principal).getUsername();
        User user = userRepository.findByUserId(userId).orElseThrow();

        String sentOtp = onlineTransactionResponse.getOtp();
        String actualOtp = transaction.getOtp();

        if(!sentOtp.equals(actualOtp)) {
            return "Invalid OTP";
        }

        UserWallet userWallet = userWalletRepository.findByUserId(user.getId()).orElseThrow();
        VendorWallet vendorWallet = vendorWalletRepository.findByVendorId(onlineTransactionResponse.getVendorId()).orElseThrow();

        userWallet.setBalance(userWallet.getBalance() - transaction.getAmount());
        vendorWallet.setBalance(vendorWallet.getBalance() + transaction.getAmount());

        transaction.setStatus("approved");

        userWalletRepository.save(userWallet);
        vendorWalletRepository.save(vendorWallet);
        transactionRepository.save(transaction);

        return "Transaction successful";
    }


    private String generateOtp() {
        // Generate a random 6-digit OTP
        return String.format("%06d", new Random().nextInt(999999));
    }

    @Override
    public String offlineTransaction(OfflineTransactionRequest offlineTransactionRequest) {

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String userId = ((UserDetails)principal).getUsername();
            String vendorId = offlineTransactionRequest.getVendorId();

            User user = userRepository.findByUserId(userId).orElseThrow();
            User vendor = userRepository.findByUserId(vendorId).orElseThrow();


            UserWallet userWallet = userWalletRepository.findByUserId(user.getId()).orElseThrow();

            if(userWallet.getOfflineBalance() < offlineTransactionRequest.getAmount()) {
                throw new RuntimeException("Insufficient balance");
            }


            String code = offlineTransactionRequest.getCode();
            Set<String> user_codes = userWallet.getCodes();

            if(!user_codes.contains(code)){
                throw new RuntimeException("Invalid code");
            }

            double gpsLocationXOfUser = offlineTransactionRequest.getGpsLocationX();
            double gpsLocationYOfUser = offlineTransactionRequest.getGpsLocationY();

            double gpsLocationXOfVendor = vendor.getGpsLocationX();
            double gpsLocationYOfVendor = vendor.getGpsLocationY();

            double distance = distanceBetweenTwoPoints(gpsLocationXOfUser, gpsLocationYOfUser, gpsLocationXOfVendor, gpsLocationYOfVendor);

            //System.out.println("Distance is:"+distance);

            if(distance > 20){
                var transaction = Transaction.builder()
                        .user1(user.getUserId())
                        .user2(vendor.getUserId())
                        .date(LocalDateTime.now())
                        .amount(offlineTransactionRequest.getAmount())
                        .status("flagged")
                        .build();

                Transaction savedTransaction = transactionRepository.save(transaction);

                return savedTransaction.getId() +" is flagged for distance";
            }

            var transaction = Transaction.builder()
                    .user1(user.getUserId())
                    .user2(vendor.getUserId())
                    .date(LocalDateTime.now())
                    .amount(offlineTransactionRequest.getAmount())
                    .status("approved")
                    .build();

        VendorWallet vendorWallet = vendorWalletRepository.findByVendorId(vendor.getId()).orElseThrow();

        userWallet.setOfflineBalance(userWallet.getOfflineBalance() - transaction.getAmount());
        vendorWallet.setBalance(vendorWallet.getBalance() + transaction.getAmount());

        transactionRepository.save(transaction);
        userWalletRepository.save(userWallet);
        vendorWalletRepository.save(vendorWallet);

        return "Transaction successful";
    }

    private double distanceBetweenTwoPoints(double lat1, double lon1, double lat2, double lon2) {

        double r = 6371; // Earth's radius in kilometers
        double p = Math.PI / 180;

        double a = 0.5 - Math.cos((lat2 - lat1) * p) / 2 +
                Math.cos(lat1 * p) * Math.cos(lat2 * p) * (1 - Math.cos((lon2 - lon1) * p)) / 2;

        return 2 * r * Math.asin(Math.sqrt(a));
        }

        public static double haversin(double val) {
            return Math.pow(Math.sin(val / 2), 2);
        }
}
