package com.hollyleo.api_gateway.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

  @NotBlank(message = "Username can't be empty")
  private String username;
  @NotBlank(message = "Password can't be empty")
  private String password;
}
