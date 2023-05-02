package com.jcst.dentalclinic.controller;

import com.jcst.dentalclinic.customResponses.ApiError;
import com.jcst.dentalclinic.models.dto.AuthenticationRequest;
import com.jcst.dentalclinic.models.dto.AuthenticationResponse;
import com.jcst.dentalclinic.models.dto.RegisterRequest;
import com.jcst.dentalclinic.services.AuthService;
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

    @Operation(summary="Register a new user")
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

    @Operation(summary="Authenticate a user for log in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(
                    mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)
            )}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(
                    mediaType = "application/json", schema = @Schema(implementation = ApiError.class)
            )}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(
                    mediaType = "application/json", schema = @Schema(implementation = ApiError.class)
            )}),
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
