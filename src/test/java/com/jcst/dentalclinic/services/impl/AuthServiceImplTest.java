package com.jcst.dentalclinic.services.impl;

import com.jcst.dentalclinic.auth.config.JwtService;
import com.jcst.dentalclinic.customExceptions.EntityAlreadyExistsException;
import com.jcst.dentalclinic.models.User;
import com.jcst.dentalclinic.models.dto.AuthenticationRequest;
import com.jcst.dentalclinic.models.dto.AuthenticationResponse;
import com.jcst.dentalclinic.models.dto.RegisterRequest;
import com.jcst.dentalclinic.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder encoder;
    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    AuthServiceImpl authService;

    @Test
    void givenAValidRegisterRequest_WhenRegisterInvoked_ThenSaveToBeCalled() {
        RegisterRequest newUser = RegisterRequest
                .builder()
                .firstName("Juan Carlos")
                .lastName("Sierra")
                .email("test@test.com")
                .password("password")
                .build();

        var token = "ThisIsATestToken";

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(User.class))).thenReturn(token);

        var registeredUser = authService.register(newUser);

        assertEquals(registeredUser.getClass(), AuthenticationResponse.class);
        assertEquals(registeredUser.getToken(), token);
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void givenAnExistingUserEmail_WhenRegisterInvoked_ThenThrowAnError() {
        RegisterRequest newUser = RegisterRequest
                .builder()
                .firstName("Juan Carlos")
                .lastName("Sierra")
                .email("test@test.com")
                .password("password")
                .build();

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(mock(User.class)));
        assertThrows(EntityAlreadyExistsException.class, ()-> authService.register(newUser));
    }



    @Test
    void givenAnAuthenticationRequest_whenAuthenticateInvoked_thenReturnAuthenticationResponseWithTheMockedToken() {
        var user = AuthenticationRequest
                .builder()
                .email("test@test.com")
                .password("password")
                .build();

        var token = "ThisIsATestToken";

        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(mock(User.class)));
        when(jwtService.generateToken(any())).thenReturn(token);

        var authenticatedUser = authService.authenticate(user);

        assertEquals(authenticatedUser.getClass(), AuthenticationResponse.class);
        assertEquals(authenticatedUser.getToken(), token);
    }

    @Test
    void givenInvalidAnAuthenticationRequest_whenAuthenticateInvoked_thenThrowAnExceptionAndGenerateTokenWontBeCalled() {
        var user = AuthenticationRequest
                .builder()
                .email("test@test.com")
                .password("password")
                .build();

        when(authenticationManager.authenticate(any())).thenThrow(mock(AuthenticationException.class));

        assertThrows(AuthenticationException.class, ()-> authService.authenticate(user));
        verify(userRepository, times(0)).findByEmail(any());
        verify(jwtService, times(0)).generateToken(any());
    }
}