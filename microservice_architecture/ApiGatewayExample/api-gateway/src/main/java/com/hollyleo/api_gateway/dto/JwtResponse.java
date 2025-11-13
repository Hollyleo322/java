package com.hollyleo.api_gateway.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtResponse {
  private final String accessToken;
  private final String refreshToken;
}
