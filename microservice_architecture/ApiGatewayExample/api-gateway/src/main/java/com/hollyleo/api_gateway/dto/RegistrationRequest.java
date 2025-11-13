package com.hollyleo.api_gateway.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationRequest {
  @NotBlank(message = "Username can't be empty")
  private final String username;
  @NotBlank(message = "Password can't be empty")
  private final String password;
}
