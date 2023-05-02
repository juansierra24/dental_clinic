package com.jcst.dentalclinic.services;

import com.jcst.dentalclinic.models.dto.AuthenticationRequest;
import com.jcst.dentalclinic.models.dto.AuthenticationResponse;
import com.jcst.dentalclinic.models.dto.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
