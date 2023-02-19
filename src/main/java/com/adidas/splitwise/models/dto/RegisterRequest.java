package com.adidas.splitwise.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Register Object")
public class RegisterRequest {
    @NotBlank(message = "Name is required and cannot be an empty String")
    private String firstName;
    private String lastName;
    @Email(message = "Please enter a valid email")
    private String email;
    @Size(min = 7, max = 20, message = "Password length must be between 7 and 20 characters")
    private String password;
}
