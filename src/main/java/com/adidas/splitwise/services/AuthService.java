package com.adidas.splitwise.services;

import com.adidas.splitwise.models.dto.AuthenticationRequest;
import com.adidas.splitwise.models.dto.AuthenticationResponse;
import com.adidas.splitwise.models.dto.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
