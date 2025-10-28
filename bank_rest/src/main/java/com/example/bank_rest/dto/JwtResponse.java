package com.example.bank_rest.dto;

import lombok.Data;

@Data
public class JwtResponse {

  private final String accessToken;

  private final String refreshToken;
}
