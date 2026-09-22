package com.example.bank_rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateAccessRequest {
  @NotBlank(message = "Token can't be empty")
  private final String refreshToken;
}
