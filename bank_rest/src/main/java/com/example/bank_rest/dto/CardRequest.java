package com.example.bank_rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CardRequest {
  @NotNull
  @Positive
  private final Long ownerId;
  @NotBlank(message ="Number can't be empty")
  private final String number;
}
