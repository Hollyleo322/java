package com.example.bank_rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class CreateUserRequest {
  @NotEmpty(message = "Username can't be empty" )
  private final String username;
  @NotEmpty(message = "Password can't be empty")
  private final String password;
}
