package com.hollyleo.api_gateway.dto;

import lombok.Data;

@Data
public class JwtResponse {
  private final String accessToken;
  private final String refreshToken;
}
