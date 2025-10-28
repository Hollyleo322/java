package com.example.bank_rest.security.jwt.jwtentity;

import lombok.Data;

import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtTokenService {

  private final String accessToken;

  private final String refreshToken;
}
