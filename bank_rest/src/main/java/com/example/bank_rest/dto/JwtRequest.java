package com.example.bank_rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtRequest {
  @NotBlank(message = "Username can't be empty")
  private final String username;
  @NotBlank(message = "Password can't be empty")
  private final String password;
}
