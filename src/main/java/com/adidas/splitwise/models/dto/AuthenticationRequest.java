package com.adidas.splitwise.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Email(message = "Please enter a valid email")
    private String email;
    @Size(min = 7, max = 20, message = "Password length must be between 7 and 20 characters")
    private String password;
}
