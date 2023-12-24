package com.example.demo.service;

import com.example.demo.models.AllocateFunds;
import com.example.demo.models.AuthenticateRequest;
import com.example.demo.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String registerUser(User user);
    String authenticateUser(AuthenticateRequest request);
    String allocateFunds(AllocateFunds funds);
    String allocateOfflineFunds(AllocateFunds funds);
}
