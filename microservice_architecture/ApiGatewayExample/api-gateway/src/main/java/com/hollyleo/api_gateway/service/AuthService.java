package com.hollyleo.api_gateway.service;

import com.hollyleo.api_gateway.dto.AuthRequest;
import com.hollyleo.api_gateway.dto.JwtResponse;
import com.hollyleo.api_gateway.dto.JwtUser;
import com.hollyleo.api_gateway.exception.IncorrectUsernameOrPasswordException;
import com.hollyleo.api_gateway.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtProvider jwtProvider;

  public JwtResponse authentication(AuthRequest authRequest) throws IncorrectUsernameOrPasswordException {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    } catch (AuthenticationException e) {
      throw new IncorrectUsernameOrPasswordException("Username or password is incorrect");
    }
    var jwtUser = new JwtUser(authRequest.getUsername());
    userService.getRolesByUsername(jwtUser.getUsername()).forEach((role) -> jwtUser.getRoles().add(role));
    return new JwtResponse(jwtProvider.generateAccessToken(jwtUser),
        jwtProvider.generateRefreshToken(jwtUser));
  }
}
