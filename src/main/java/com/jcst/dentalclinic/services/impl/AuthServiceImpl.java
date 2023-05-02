package com.jcst.dentalclinic.services.impl;

import com.jcst.dentalclinic.auth.config.JwtService;
import com.jcst.dentalclinic.customExceptions.EntityAlreadyExistsException;
import com.jcst.dentalclinic.models.Role;
import com.jcst.dentalclinic.models.User;
import com.jcst.dentalclinic.models.dto.AuthenticationRequest;
import com.jcst.dentalclinic.models.dto.AuthenticationResponse;
import com.jcst.dentalclinic.models.dto.RegisterRequest;
import com.jcst.dentalclinic.repository.UserRepository;
import com.jcst.dentalclinic.services.AuthService;
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
