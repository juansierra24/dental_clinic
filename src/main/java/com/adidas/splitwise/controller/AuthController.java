package com.adidas.splitwise.controller;

import com.adidas.splitwise.customResponses.ApiError;
import com.adidas.splitwise.models.dto.AuthenticationRequest;
import com.adidas.splitwise.models.dto.AuthenticationResponse;
import com.adidas.splitwise.models.dto.RegisterRequest;
import com.adidas.splitwise.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary="Sign up a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED", content = {@Content(
                    mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)
            )}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(
                    mediaType = "application/json", schema = @Schema(implementation = ApiError.class)
            )}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(
                    mediaType = "application/json", schema = @Schema(implementation = ApiError.class)
            )}),
            @ApiResponse(responseCode = "409", description = "CONFLICT - User already exists", content = {@Content(
                    mediaType = "application/json", schema = @Schema(implementation = ApiError.class)
            )}),
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }



}
