package com.adidas.splitwise.services.impl;

import com.adidas.splitwise.auth.config.JwtService;
import com.adidas.splitwise.customExceptions.EntityAlreadyExistsException;
import com.adidas.splitwise.models.Role;
import com.adidas.splitwise.models.User;
import com.adidas.splitwise.models.dto.AuthenticationRequest;
import com.adidas.splitwise.models.dto.AuthenticationResponse;
import com.adidas.splitwise.models.dto.RegisterRequest;
import com.adidas.splitwise.repository.UserRepository;
import com.adidas.splitwise.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> userEntry = userRepository.findByEmail(request.getEmail());
        if(userEntry.isPresent()){
            throw new EntityAlreadyExistsException(User.class, "email", request.getEmail());
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
