package com.jcst.dentalclinic.controller;

import com.jcst.dentalclinic.models.dto.AuthenticationRequest;
import com.jcst.dentalclinic.models.dto.AuthenticationResponse;
import com.jcst.dentalclinic.models.dto.RegisterRequest;
import com.jcst.dentalclinic.services.AuthService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import java.sql.Connection;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AuthControllerTest{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    AuthService authService;
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConn;
    @InjectMocks
    AuthController authController;
    private Gson gson = new Gson();

    @Test
    void givenARegisterRequest_whenRegisterEndpointCalled_thenCallRegisterAndReturnMockedToken() throws Exception {

        RegisterRequest newUser = RegisterRequest
                .builder()
                .firstName("Juan")
                .lastName("Sierra")
                .email("test@test.com")
                .password("password")
                .build();

        var response = AuthenticationResponse.builder().token("test").build();

        when(authService.register(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(response.getToken()));

        verify(authService, times(1)).register(any());
    }

    @Test
    void givenAnInvalidRegisterRequest_whenRegisterEndpointCalled_thenBadRequest() throws Exception{
        RegisterRequest newUser = RegisterRequest
                .builder()
                .firstName("")
                .lastName("Sierra")
                .email("testtest.com")
                .password("pass")
                .build();

        var errors = new ArrayList<String>();
        errors.add("email: Please enter a valid email");
        errors.add("firstName: Name is required and cannot be an empty String");
        errors.add("password: Password length must be between 7 and 20 characters");

        var response = AuthenticationResponse.builder().token("test").build();

        when(authService.register(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(newUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItems(errors.get(0), errors.get(1), errors.get(2))));

        verify(authService, times(0)).register(any());
    }

    @Test
    void givenAAuthenticationRequest_whenAuthenticateEndpointCalled_thenCallAuthenticateAndReturnMockedToken() throws Exception{
        var user = AuthenticationRequest
                .builder()
                .email("test@test.com")
                .password("password")
                .build();

        var response = AuthenticationResponse.builder().token("test").build();

        when(authService.authenticate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(response.getToken()));

        verify(authService, times(1)).authenticate(any());
    }

    @Test
    void givenAnInvalidAuthenticationRequest_whenAuthenticateEndpointCalled_thenBadRequest() throws Exception{
        var user = AuthenticationRequest
                .builder()
                .email("testtest.com")
                .password("pass")
                .build();

        var errors = new ArrayList<String>();
        errors.add("email: Please enter a valid email");
        errors.add("password: Password length must be between 7 and 20 characters");

        var response = AuthenticationResponse.builder().token("test").build();

        when(authService.authenticate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItems(errors.get(0), errors.get(1))));

        verify(authService, times(0)).authenticate(any());
    }
}