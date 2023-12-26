package com.example.demo.service;

import com.example.demo.payment.models.AllocateFunds;
import com.example.demo.payment.models.AuthenticateRequest;
import com.example.demo.payment.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String registerUser(User user);
    String authenticateUser(AuthenticateRequest request);
    String allocateFunds(AllocateFunds funds);
    String allocateOfflineFunds(AllocateFunds funds);
}
